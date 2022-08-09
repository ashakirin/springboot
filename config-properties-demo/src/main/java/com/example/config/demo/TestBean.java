package com.example.config.demo;

import org.springframework.stereotype.Component;

@Component
public class TestBean {
    private final String field1 = "test";

    public String getField1() {
        return field1;
    }
}
