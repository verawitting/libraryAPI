package com.example.libraryapi.dto;

import java.io.Serializable;

public record AuthorResponse(
    Long id,
    String name
) implements Serializable {}