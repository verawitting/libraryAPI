package com.example.libraryapi.dto;

import java.time.LocalDate;

public record LoanResponse (
    Long id,
    Long bookId,
    String bookTitle,
    LocalDate loanDate,
    LocalDate returnDate
) {}
