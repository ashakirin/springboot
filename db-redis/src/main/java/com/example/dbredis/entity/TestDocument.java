package com.example.dbredis.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TestDocument {
    @Id
    public String id;
    public String name;
    public String description;
    public int volume;
}
