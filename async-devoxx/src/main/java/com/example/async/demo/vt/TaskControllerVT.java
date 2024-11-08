package com.example.async.demo.vt;

import com.example.async.demo.TaskService;
import com.example.async.demo.TaskServiceFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@RestController
public class TaskControllerVT {
    private final TaskServiceFuture taskServiceFuture;
    private final TaskService taskService;

    public TaskControllerVT(TaskServiceFuture taskServiceFuture, TaskService taskService) {
        this.taskServiceFuture = taskServiceFuture;
        this.taskService = taskService;
    }

    @GetMapping("/run-tasks-vt")
    public String runTasks() throws InterruptedException, ExecutionException {
        Future<String> task1 = taskService.processTask1();
        Future<String> task2 = taskService.processTask2();
        Future<String> task3 = taskService.processTask3();

        String combinedResult = task1.get() + "," + task2.get() + "," + task3.get();

        String composedResult = taskService.processTask4(combinedResult).get();
        return "Results: " + combinedResult + "; " + composedResult;
    }
}
