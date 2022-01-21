package com.example.demodbeclipselinktest.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Book")
public class BookEntity {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String test;

//    @OneToMany(mappedBy="id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<AuthorEntity> authorEntities = new ArrayList<>();
//
    public BookEntity() {
    }

//    public BookEntity(String name, AuthorEntity authorEntity) {
//        this.id = id;
//        this.name = name;
//        this.authorEntities.add(authorEntity);
//    }

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

//    public List<AuthorEntity> getAuthorEntities() {
//        return authorEntities;
//    }
}
