package com.example.async.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {
    @Async
    public CompletableFuture<String> processTask1() throws InterruptedException {
        Thread.sleep(2000);
        return CompletableFuture.completedFuture("Task 1 completed");
    }

    @Async
    public CompletableFuture<String> processTask2() throws InterruptedException {
        Thread.sleep(3000);
        return CompletableFuture.completedFuture("Task 2 completed");
    }

    @Async
    public CompletableFuture<String> processTask3() throws InterruptedException {
        Thread.sleep(1000);
        return CompletableFuture.completedFuture("Task 3 completed");
    }

    @Async
    public CompletableFuture<String> processTask4(String previousResult) {
        return CompletableFuture.completedFuture("Task 4 used previous result: " + previousResult);
    }
}
