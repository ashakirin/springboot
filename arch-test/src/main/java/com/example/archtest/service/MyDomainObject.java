package com.example.archtest.service;

import org.springframework.stereotype.Service;

public class MyDomainObject {
    private String id;
    private String content;

    public MyDomainObject(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
