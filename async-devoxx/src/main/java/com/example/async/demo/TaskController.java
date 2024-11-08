package com.example.async.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@RestController
public class TaskController {
    private final TaskServiceFuture taskServiceFuture;
    private final TaskService taskService;

    public TaskController(TaskServiceFuture taskServiceFuture, TaskService taskService) {
        this.taskServiceFuture = taskServiceFuture;
        this.taskService = taskService;
    }

    @GetMapping("/run-tasks-future")
    public String runTasksFuture() throws InterruptedException, ExecutionException {
        Future<String> task1 = taskServiceFuture.processTask1();
        Future<String> task2 = taskServiceFuture.processTask2();
        Future<String> task3 = taskServiceFuture.processTask3();

        String result1 = task1.get();
        String result2 = task2.get();
        String result3 = task3.get();

        return "Results: " + result1 + ", " + result2 + ", " + result3;
    }

    @GetMapping("/run-tasks")
    public String runTasks() throws InterruptedException, ExecutionException {
        CompletableFuture<String> task1 = taskService.processTask1();
        CompletableFuture<String> task2 = taskService.processTask2();
        CompletableFuture<String> task3 = taskService.processTask3();

        String combinedResult = task1
                .thenCombine(task2, (a, b) -> a + "," + b)
                .thenCombine(task3, (a, b) -> a + "," + b)
                .get();

        String composedResult = task1
                .thenCombine(task2, (a, b) -> a + "," + b)
                .thenCombine(task3, (a, b) -> a + "," + b)
                .thenCompose(r -> taskService.processTask4(r))
                .get();

        return "Results: " + combinedResult + "; " + composedResult;
    }

    @GetMapping("/run-tasks-async")
    public String runTasksAsync() throws InterruptedException, ExecutionException {
        CompletableFuture<String> task1 = taskService.processTask1();
        CompletableFuture<String> task2 = taskService.processTask2();
        Executor executor = Executors.newFixedThreadPool(3);

        String combinedResult = task1
                .thenCombineAsync(task2, (a, b) -> a + "," + b, executor)
                .get();

        String composedResult = task1
                .thenComposeAsync(taskService::processTask4, executor)
                .get();

        return "Results: " + combinedResult + "; " + composedResult;
    }
}
