package com.example.libraryapi.dto;

import com.example.libraryapi.model.Author;

public class BookResponseV2 {
    private String title;
    private String author;
    private boolean available;

    public BookResponseV2(String title, String author, boolean available) {
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }
}
