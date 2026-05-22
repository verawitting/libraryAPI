package com.example.libraryapi.controller.v2;

import com.example.libraryapi.dto.v2.BookListResponseV2;
import com.example.libraryapi.dto.v2.BookResponseV2;
import com.example.libraryapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/books")
public class BookControllerV2 {

    private final BookService service;

    public BookControllerV2(BookService service) {
        this.service = service;
    }

    @Operation(summary = "Get all books (v2)")
    @GetMapping
    public BookListResponseV2 getAllBooks(Pageable pageable) {
        List<BookResponseV2> books = service.getAllBooks(pageable)
                .stream()
                .map(book -> new BookResponseV2(
                    book.id(),
                    book.title(),
                    book.author(),
                    book.isbn(),
                    book.publishedYear(),
                    "Main Branch"
                ))
                .toList();
        return new BookListResponseV2(books);
    }
}