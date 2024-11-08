package com.example.async.demo.vt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfigVT {

    @Bean(name = "executorService")
    public ExecutorService executorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}