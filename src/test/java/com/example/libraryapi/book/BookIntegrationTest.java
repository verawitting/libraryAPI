package com.example.libraryapi.book;

import com.example.libraryapi.BaseIntegrationTest;
import com.example.libraryapi.dto.AuthorResponse;
import com.example.libraryapi.dto.AuthorRequest;
import com.example.libraryapi.dto.BookRequest;
import com.example.libraryapi.dto.BookResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateBook() {
        AuthorRequest author = new AuthorRequest("Test Author");

        Long authorId = authenticatedAdmin()
                .postForEntity("/api/v1/authors", author, AuthorResponse.class)
                .getBody().id();

        BookRequest request = new BookRequest(
                "Book",
                "123",
                2020,
                authorId
        );

        ResponseEntity<BookResponse> response =
                authenticatedAdmin().postForEntity("/api/v1/books", request, BookResponse.class);

        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void shouldReturnListOfAllBooks() {
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

        authenticatedAdmin().postForEntity("/api/v1/books", request, BookResponse.class);

        ResponseEntity<BookResponse[]> response =
                authenticatedUser().getForEntity("/api/v1/books", BookResponse[].class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().length);
    }

    @Test
    void shouldReturnEmptyListWhenNoBooksExist() {
        ResponseEntity<BookResponse[]> response =
                authenticatedUser().getForEntity("/api/v1/books", BookResponse[].class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(0, response.getBody().length);
    }

    @Test
    void shouldGetBookById() {
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

        Long id = authenticatedAdmin()
                .postForEntity("/api/v1/books", request, BookResponse.class)
                .getBody().id();

        ResponseEntity<BookResponse> response =
                authenticatedUser().getForEntity("/api/v1/books/" + id, BookResponse.class);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void shouldReturn404WhenBookNotFound() {
        ResponseEntity<String> response =
                authenticatedUser().getForEntity("/api/v1/books/999", String.class);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void shouldReturn400WhenInvalidBook() {
        String invalidJson = "{}";

        ResponseEntity<String> response =
                authenticatedAdmin().postForEntity("/api/v1/books", invalidJson, String.class);

        assertEquals(400, response.getStatusCode().value());
    }
}