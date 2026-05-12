package com.example.libraryapi.dto;

public class AuthorResponse {
    private Long id;
    private String name;
    private int numberOfBooks;

    public AuthorResponse(Long id, String name, int numberOfBooks) {
        this.id = id;
        this.name = name;
        this.numberOfBooks = numberOfBooks;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }
}
