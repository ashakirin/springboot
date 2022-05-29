package com.example.arch.archtestdemo.service;

import com.example.arch.archtestdemo.repository.TestRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class TestService {
    private TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Async
    public CompletableFuture<String> parallel(int i) {
        try {
            System.out.println("starting job" + i);
            Thread.sleep(2000);
            System.out.println("finishing job" + i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("hello");
    }
}
