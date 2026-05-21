package com.example.libraryapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoanRequest(
    @NotNull(message = "Book id is required")
    @Positive(message = "Book id must be positive")
    Long bookId
) {}
