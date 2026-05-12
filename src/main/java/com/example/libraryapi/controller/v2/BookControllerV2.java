package com.example.libraryapi.controller;

import com.example.libraryapi.dto.BookListResponseV2;
import com.example.libraryapi.dto.BookResponseV2;
import com.example.libraryapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all books")
    @GetMapping
    public BookListResponseV2 getAllBooks() {
        List<BookResponseV2> books = service.getAllBooks()
                .stream()
                .map(book -> new BookResponseV2(
                        book.getTitle(),
                        book.getAuthor(),
                        true
                ))
                .toList();
        return new BookListResponseV2(books);
    }
}
