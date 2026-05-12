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
        AuthorRequest request = new AuthorRequest();
        request.setName("Test Author");

        Long id = restTemplate
                .postForEntity("/api/v1/authors", request, AuthorResponse.class)
                .getBody().getId();

        ResponseEntity<AuthorResponse> response =
                restTemplate.getForEntity("/api/v1/authors/" + id, AuthorResponse.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(request.getName(), response.getBody().getName());
    }

    @Test
    void shouldReturn404WhenAuthorNotFound() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/api/v1/authors/999", String.class);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void shouldGetBooksByAuthor() {
        AuthorRequest author = new AuthorRequest();
        author.setName("Test Author");

        Long authorId = restTemplate
                .postForEntity("/api/v1/authors", author, AuthorResponse.class)
                .getBody().getId();

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
        AuthorRequest author = new AuthorRequest();
        author.setName("Empty Author");

        Long authorId = restTemplate
                .postForEntity("/api/v1/authors", author, AuthorResponse.class)
                .getBody().getId();

        ResponseEntity<BookResponse[]> response =
                restTemplate.getForEntity("/api/v1/authors/" + authorId + "/books", BookResponse[].class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(0, response.getBody().length);
    }

    @Test
    void shouldFailWhenCreatingAuthorWithoutName() {
        AuthorRequest request = new AuthorRequest();

        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/v1/authors", request, String.class);

        assertEquals(400, response.getStatusCode().value());
    }
}
