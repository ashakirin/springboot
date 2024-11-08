package com.example.async.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class TaskServiceFuture {
    @Async("taskExecutor")
    public Future<String> processTask1() throws InterruptedException {
        Thread.sleep(2000);
        return new AsyncResult<>("Task 1 completed");
    }

    @Async("taskExecutor")
    public Future<String> processTask2() throws InterruptedException {
        Thread.sleep(3000);
        return new AsyncResult<>("Task 2 completed");
    }

    @Async("taskExecutor")
    public Future<String> processTask3() throws InterruptedException {
        Thread.sleep(1000);
        return new AsyncResult<>("Task 3 completed");
    }
}
