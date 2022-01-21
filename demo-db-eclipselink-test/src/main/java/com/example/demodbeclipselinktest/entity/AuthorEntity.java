package com.example.demodbeclipselinktest.entity;

import javax.persistence.*;

@Entity
@Table(name = "Author")
public class AuthorEntity {
    @Id
    @GeneratedValue
    private int id;

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

    public void setName(String name) {
        this.name = name;
    }
}

