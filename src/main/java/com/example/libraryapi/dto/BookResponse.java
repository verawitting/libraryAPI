package com.example.libraryapi.dto;

import java.io.Serializable;

public record BookResponse(
    Long id,
    String title,
    String author,
    String isbn,
    int publishedYear
) implements Serializable {}
