package com.example.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRequest (
    
    @NotBlank
    @Size(min = 2, max = 100)
    @Schema(description = "Title of the book", example = "To Kill A Mockingbird")
    String title,

    @Size(max = 20)
    @Schema(description = "ISBN of the book", example = "123")
    String isbn,

    @Min(1000)
    @Max(2026)
    @Schema(description = "The year the book was published", example = "1960")
    int publishedYear,

    @NotNull
    @Schema(description = "The id of the author", example = "1")
    Long authorId
) {}

