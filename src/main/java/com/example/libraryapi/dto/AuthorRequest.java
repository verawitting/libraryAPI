package com.example.libraryapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorRequest (
    
    @NotBlank(message = "Author name cannot be blank")
    @Size(min = 2, max = 100)
    String name

) {}
