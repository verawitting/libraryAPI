package com.example.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest (
    
    @NotBlank
    @Schema(description = "Title of the book", example = "To Kill A Mockingbird")
    String title,

    @Schema(description = "ISBN of the book", example = "123")
    String isbn,

    @Schema(description = "The year the book was published", example = "1960")
    int publishedYear,

    @NotNull
    @Schema(description = "The id of the author", example = "1")
    Long authorId
) {}

