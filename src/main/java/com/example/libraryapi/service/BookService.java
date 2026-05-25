package com.example.libraryapi.service;

import com.example.libraryapi.dto.BookRequest;
import com.example.libraryapi.dto.BookResponse;
import com.example.libraryapi.exception.BookNotFoundException;
import com.example.libraryapi.model.Author;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.repository.AuthorRepository;
import com.example.libraryapi.repository.BookRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    @CacheEvict(value= "books", allEntries = true)
    public BookResponse createBook(BookRequest request) {
        Author author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Book book = new Book();
        book.setTitle(request.title());
        book.setIsbn(request.isbn());
        book.setPublishedYear(request.publishedYear());
        book.setAuthor(author);
        
        return mapToResponse(repository.save(book));
    }

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return repository.findAll(pageable)
            .map(this::mapToResponse);
    }

    @Cacheable(value = "books", key = "#id")
    public BookResponse getBookById(Long id) {

        // adding timing to show redis efficientness
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return mapToResponse(book);
    }

    @CacheEvict(value = "books", allEntries = true)
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        Author author = authorRepository.findById(request.authorId())
            .orElseThrow(() -> new RuntimeException("Author not found"));

        book.setTitle(request.title());
        book.setIsbn(request.isbn());
        book.setPublishedYear(request.publishedYear());
        book.setAuthor(author);

        return mapToResponse(repository.save(book));
    }

    @CacheEvict(value = "books", allEntries = true)
    public void deleteBook(Long id) {
        if (!repository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
            book.getId(),
            book.getTitle(),
            book.getAuthor().getName(),
            book.getIsbn(),
            book.getPublishedYear()
        );
    }
}
