package com.example.batchdemo.entity;

import javax.persistence.*;

@Entity
@Table(name = "AUTHOR")
public class AuthorEntity {
    @Id
    @GeneratedValue
    private int id;

    @Column(name="BOOKID")
    private int bookId;

    private String name;

    public AuthorEntity() {
    }

    public AuthorEntity(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
