package com.example.demospringapp.service;

import org.springframework.stereotype.Component;

@Component
public class DemoSpringService {

    public String demoOperation(String param) {
        return "Hello world: " + param;
    }
}
