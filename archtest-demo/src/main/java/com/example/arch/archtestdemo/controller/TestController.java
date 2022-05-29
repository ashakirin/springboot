package com.example.arch.archtestdemo.controller;

import com.example.arch.archtestdemo.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@RestController
public class TestController {
    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/hello")
    public String testGet() {
        IntStream.range(0, 10)
                .forEach( i -> {
                    CompletableFuture<String> future = testService.parallel(i);
                });
        return "test";
    }
}
