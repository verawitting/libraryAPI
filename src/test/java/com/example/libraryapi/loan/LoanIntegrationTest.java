package com.example.libraryapi.loan;

import com.example.libraryapi.BaseIntegrationTest;
import com.example.libraryapi.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateLoan() {
        AuthorRequest author = new AuthorRequest("Author");

        Long authorId = authenticatedAdmin()
                .postForEntity("/api/v1/authors", author, AuthorResponse.class)
                .getBody().id();

        BookRequest request = new BookRequest(
                "Book",
                "123",
                2020,
                authorId
        );

        Long bookId = authenticatedAdmin()
                .postForEntity("/api/v1/books", request, BookResponse.class)
                .getBody().id();

        LoanRequest loan = new LoanRequest(bookId);

        ResponseEntity<LoanResponse> response =
                authenticatedAdmin().postForEntity("/api/v1/loans", loan, LoanResponse.class);

        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void shouldReturn400WhenLoaningSameBookTwice() {
        AuthorRequest author = new AuthorRequest("Author");

        Long authorId = authenticatedAdmin()
                .postForEntity("/api/v1/authors", author, AuthorResponse.class)
                .getBody().id();

        BookRequest request = new BookRequest(
                "Book",
                "123",
                2020,
                authorId
        );

        Long bookId = authenticatedAdmin()
                .postForEntity("/api/v1/books", request, BookResponse.class)
                .getBody().id();

        LoanRequest loan = new LoanRequest(bookId);

        authenticatedAdmin().postForEntity("/api/v1/loans", loan, String.class);

        ResponseEntity<String> second =
                authenticatedAdmin().postForEntity("/api/v1/loans", loan, String.class);

        assertEquals(400, second.getStatusCode().value());
    }

    @Test
    void shouldGetAllLoans() {
        AuthorRequest author = new AuthorRequest("Author");

        Long authorId = authenticatedAdmin()
                .postForEntity("/api/v1/authors", author, AuthorResponse.class)
                .getBody().id();

        BookRequest request = new BookRequest(
                "Book",
                "123",
                2020,
                authorId
        );

        Long bookId = authenticatedAdmin()
                .postForEntity("/api/v1/books", request, BookResponse.class)
                .getBody().id();

        LoanRequest loan = new LoanRequest(bookId);

        authenticatedAdmin().postForEntity("/api/v1/loans", loan, LoanResponse.class);

        ResponseEntity<LoanResponse[]> response =
                authenticatedUser().getForEntity("/api/v1/loans", LoanResponse[].class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().length);
    }

    @Test
    void shouldFailWithConcurrentLoans() throws Exception {
        AuthorRequest author = new AuthorRequest("Author");

        Long authorId = authenticatedAdmin()
                .postForEntity("/api/v1/authors", author, AuthorResponse.class)
                .getBody().id();

        BookRequest request = new BookRequest(
                "Book",
                "123",
                2020,
                authorId
        );

        Long bookId = authenticatedAdmin()
                .postForEntity("/api/v1/books", request, BookResponse.class)
                .getBody().id();

        int numberOfRequests = 20;
        ExecutorService executor = Executors.newFixedThreadPool(20);

        List<Callable<ResponseEntity<String>>> tasks = new ArrayList<>();

        for (int i = 0; i < numberOfRequests; i++) {
            tasks.add(() -> {
                LoanRequest loan = new LoanRequest(bookId);
                return authenticatedAdmin().postForEntity("/api/v1/loans", loan, String.class);
            });
        }

        List<Future<ResponseEntity<String>>> results = executor.invokeAll(tasks);

        executor.shutdown();

        long successCount = results.stream()
                .map(f -> {
                    try {
                        return f.get().getStatusCode().value();
                    } catch (Exception e) {
                        return 500;
                    }
                })
                .filter(status -> status == 201)
                .count();
        System.out.println("Successful loans: " + successCount);

        assertEquals(1, successCount);
    }
}
