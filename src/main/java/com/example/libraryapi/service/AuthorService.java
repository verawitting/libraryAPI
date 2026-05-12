package com.example.libraryapi.service;

import com.example.libraryapi.dto.AuthorResponse;
import com.example.libraryapi.dto.AuthorRequest;
import com.example.libraryapi.dto.BookResponse;
import com.example.libraryapi.model.Author;
import com.example.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public AuthorResponse createAuthor(AuthorRequest request) {
        Author author = new Author(request.getName());
        Author saved = repository.save(author);

        return new AuthorResponse(
                saved.getId(),
                saved.getName(),
                0
        );
    }

    public AuthorResponse getAuthorById(Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        int numberOfBooks = author.getBooks() != null ? author.getBooks().size() : 0;

        return new AuthorResponse(
                author.getId(),
                author.getName(),
                numberOfBooks
        );
    }

    public List<BookResponse> getBooksByAuthor(Long authorId) {
        Author author = repository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        return author.getBooks()
                .stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getIsbn(),
                        book.getPublishedYear()
                ))
                .toList();
    }

    public void updateAuthor(Long authorId) {
        //implement this!
    }

    public void deleteAuthor(Long authorId) {
        //implement this!
    }
}
