package com.example.async.demoasync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/async")
public class AsyncRestController {
    private AsyncWorker worker;

    @Autowired
    public AsyncRestController(AsyncWorker worker) {
        this.worker = worker;
    }

    @GetMapping("/start")
    public void startAsync(String test) {
        System.out.println("Calling async method. "
                + Thread.currentThread().getName());
        worker.asyncCall(test);
    }
}
