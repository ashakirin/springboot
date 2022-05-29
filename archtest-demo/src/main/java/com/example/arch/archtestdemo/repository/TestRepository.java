package com.example.arch.archtestdemo.repository;

import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {

    public String testMethod() {
        return "hello";
    }
}
