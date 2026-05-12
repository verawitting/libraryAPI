package com.example.libraryapi.dto.v2;

public record BookResponseV2(
    Long id,
    String title,
    String author,
    String isbn,
    int publishedYear,
    String libraryBranch
) {}
