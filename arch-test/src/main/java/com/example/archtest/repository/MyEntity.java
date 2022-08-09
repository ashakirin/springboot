package com.example.archtest.repository;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MyEntity {
    @Id
    private String id;

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
