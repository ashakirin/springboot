package com.example.db.entity;

import javax.persistence.*;

@Entity
@Table(name="Book")
public class BookEntity {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String test;

    @OneToOne
    private AuthorEntity authorEntity;

    public BookEntity() {
    }

    public BookEntity(String name, AuthorEntity authorEntity) {
        this.id = id;
        this.name = name;
        this.authorEntity = authorEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorEntity getAuthorEntity() {
        return authorEntity;
    }
}
