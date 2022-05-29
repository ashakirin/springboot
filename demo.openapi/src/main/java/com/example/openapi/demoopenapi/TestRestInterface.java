package com.example.openapi.demoopenapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface TestRestInterface {


    @GetMapping("/test/rest")
    default void testGet() {
        System.out.println("My default implementation");
    }
}
