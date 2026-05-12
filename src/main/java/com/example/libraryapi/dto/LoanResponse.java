package com.example.libraryapi.dto;

import java.time.LocalDate;

public class LoanResponse {
    private final Long id;
    private final Long bookId;
    private final String bookTitle;
    private final LocalDate loanDate;
    private final LocalDate returnDate;

    public LoanResponse(Long id, Long bookId, String bookTitle, LocalDate loanDate, LocalDate returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Long getId() { return id; }
    public Long getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getReturnDate() { return returnDate; }
}
