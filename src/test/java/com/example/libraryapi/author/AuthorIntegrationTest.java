package com.example.libraryapi.author;

import com.example.libraryapi.BaseIntegrationTest;
import com.example.libraryapi.dto.AuthorResponse;
import com.example.libraryapi.dto.AuthorRequest;
import com.example.libraryapi.dto.BookRequest;
import com.example.libraryapi.dto.BookResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateAndGetAuthor() {
        AuthorRequest request = new AuthorRequest("Test Author");

        Long id = restTemplate
                .postForEntity("/api/v1/authors", request, AuthorResponse.class)
                .getBody().id();

        ResponseEntity<AuthorResponse> response =
                restTemplate.getForEntity("/api/v1/authors/" + id, AuthorResponse.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(request.name(), response.getBody().name());
    }

    @Test
    void shouldReturn404WhenAuthorNotFound() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/api/v1/authors/999", String.class);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void shouldGetBooksByAuthor() {
        AuthorRequest author = new AuthorRequest("Test Author");

        Long authorId = restTemplate
                .postForEntity("/api/v1/authors", author, AuthorResponse.class)
                .getBody().id();

        BookRequest request = new BookRequest(
                "Book",
                "123",
                2020,
                authorId
        );

        restTemplate.postForEntity("/api/v1/books", request, BookResponse.class);

        ResponseEntity<BookResponse[]> response =
                restTemplate.getForEntity("/api/v1/authors/" + authorId + "/books", BookResponse[].class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().length);
    }

    @Test
    void shouldReturnEmptyBooksForAuthor() {
        AuthorRequest author = new AuthorRequest("Empty Author");

        Long authorId = restTemplate
                .postForEntity("/api/v1/authors", author, AuthorResponse.class)
                .getBody().id();

        ResponseEntity<BookResponse[]> response =
                restTemplate.getForEntity("/api/v1/authors/" + authorId + "/books", BookResponse[].class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(0, response.getBody().length);
    }

    @Test
    void shouldFailWhenCreatingAuthorWithoutName() {
        AuthorRequest request = new AuthorRequest("");

        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/v1/authors", request, String.class);

        assertEquals(401, response.getStatusCode().value());
    }
}
