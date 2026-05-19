package com.example.libraryapi.service;

import com.example.libraryapi.dto.AuthorResponse;
import com.example.libraryapi.dto.AuthorRequest;
import com.example.libraryapi.dto.BookResponse;
import com.example.libraryapi.exception.AuthorNotFoundException;
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
        Author author = new Author(request.name());
        Author saved = repository.save(author);

        return mapToResponse(saved);
    }

    public List<AuthorResponse> getAllAuthors() {
        return repository.findAll()
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    public AuthorResponse getAuthorById(Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        return mapToResponse(author);
    }

    public List<BookResponse> getBooksByAuthor(Long authorId) {
        Author author = repository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        return author.getBooks().stream()
            .map(book -> new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getIsbn(),
                book.getPublishedYear()
            ))
            .toList();
    }

    public AuthorResponse updateAuthor(Long authorId, AuthorRequest request) {
        
        Author author = repository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        author.setName(request.name());

        Author updated = repository.save(author);

        return mapToResponse(updated);
    }


    public void deleteAuthor(Long id) {
        
        if (!repository.existsById(id)) {
            throw new AuthorNotFoundException(id);
        }

        repository.deleteById(id);
    }

    private AuthorResponse mapToResponse(Author author) {

        return new AuthorResponse(
            author.getId(),
            author.getName()
        );
    }

}
