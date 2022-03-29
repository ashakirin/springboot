package com.example.mongo.testmongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Document
public class BookEntity {
    @Id
    private String id;

    private String name;

    private String isbn;

    private List<AuthorEntity> authors = new ArrayList<>();

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


    public void addAuthor(AuthorEntity authorEntity) {
        authors.add(authorEntity);
    }

    public List<AuthorEntity> getAuthors() {
        return authors;
    }
}
