package com.example.async.demo;

import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class TaskServiceFutureStandalone {
    private final ExecutorService executorService;

    public TaskServiceFutureStandalone(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Future<String> processTask1() {
        return executorService.submit(() -> {
            Thread.sleep(2000);
            return "Task 1 completed";
        });
    }

    public Future<String> processTask2() {
        return executorService.submit(() -> {
            Thread.sleep(3000);
            return "Task 2 completed";
        });
    }

    public Future<String> processTask3() {
        return executorService.submit(() -> {
            Thread.sleep(1000);
            return "Task 3 completed";
        });
    }
}
