package com.example.libraryapi.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    public Author() {}

    public Author(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Author name cannot be empty");
        }
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Author name cannot be empty");
        }
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }
}
