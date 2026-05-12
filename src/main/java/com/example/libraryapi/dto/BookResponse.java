package com.example.libraryapi.dto;

public record BookResponse(
    Long id,
    String title,
    String author,
    String isbn,
    int publishedYear
) {}
