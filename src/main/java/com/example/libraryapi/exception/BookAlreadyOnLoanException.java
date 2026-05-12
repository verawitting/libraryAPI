package com.example.libraryapi.exception;

public class BookAlreadyOnLoanException extends RuntimeException {
    public BookAlreadyOnLoanException(Long bookId) {
        super("Book with id " + bookId + " is already on loan");
    }
}