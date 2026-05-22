package com.example.libraryapi.dto.v2;

import java.util.List;

public record BookListResponseV2(
    List<BookResponseV2> data,
    String version
) {
    public BookListResponseV2(List<BookResponseV2> data) {
        this(data, "v2");
    }
}
