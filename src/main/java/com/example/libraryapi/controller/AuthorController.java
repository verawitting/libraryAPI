package com.example.libraryapi.controller;

import com.example.libraryapi.dto.AuthorResponse;
import com.example.libraryapi.dto.AuthorRequest;
import com.example.libraryapi.dto.BookResponse;
import com.example.libraryapi.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new author")
    @ApiResponse(responseCode = "201", description = "Author created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid author input")
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse created = service.createAuthor(request);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Get an author by id")
    @ApiResponse(responseCode = "200", description = "Author found successfully")
    @ApiResponse(responseCode = "404", description = "Author not found")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAuthorById(id));
    }

    @Operation(summary = "Get all authors")
    @ApiResponse(responseCode = "200", description = "List of all authors")
    @GetMapping
    public ResponseEntity<Page<AuthorResponse>> getAllAuthors(
            @ParameterObject @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(service.getAllAuthors(pageable));
    }

    @Operation(summary = "Get all books by author")
    @ApiResponse(responseCode = "200", description = "List of books by author")
    @ApiResponse(responseCode = "404", description = "Author not found")
    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBooksByAuthor(id));
    }

    @Operation(summary = "Update author")
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
        @PathVariable Long id,
        @Valid @RequestBody AuthorRequest request
    ) {
        return ResponseEntity.ok(service.updateAuthor(id, request));
    }

    @Operation(summary = "Delete author")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        service.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
