package com.example.async.demo;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TaskServiceStandalone {

    public CompletableFuture<String> processTask1() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                return "Task 1 completed";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<String> processTask2() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                return "Task 2 completed";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<String> processTask3() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                return "Task 3 completed";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
