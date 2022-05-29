package com.example.mongo.testmongo.entity;

import org.springframework.data.annotation.Id;

public class BookAggregate {
    @Id
    private String id;

    private String name;

    private String isbn;

    private LibraryEntity library;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LibraryEntity getLibrary() {
        return library;
    }

    public void setLibrary(LibraryEntity library) {
        this.library = library;
    }
}
