package com.example.libraryapi.dto;

import java.util.List;

public class BookListResponseV2 {
    private List<BookResponseV2> data;
    private String version;

    public BookListResponseV2(List<BookResponseV2> data) {
        this.data = data;
        this.version = "v2";
    }

    public List<BookResponseV2> getData() {
        return data;
    }

    public String getVersion() {
        return version;
    }
}
