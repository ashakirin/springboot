package com.example.async.demoasync;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncWorker {

    @Async
    public void asyncCall(String test) {
        System.out.println("Execute method asynchronously. "
                + Thread.currentThread().getName());
    }
}
