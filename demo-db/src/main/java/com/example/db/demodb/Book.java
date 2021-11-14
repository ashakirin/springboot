package com.example.db.demodb;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private final int id;
    private final String name;
    private final List<String> authors = new ArrayList<>();

    public Book(int id, String name, List<String> authors) {
        this.id = id;
        this.name = name;
        this.authors.addAll(authors);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getAuthors() {
        return authors;
    }
}
