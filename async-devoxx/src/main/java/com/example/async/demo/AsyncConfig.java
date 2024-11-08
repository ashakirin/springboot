package com.example.async.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);  // Number of core threads
        executor.setMaxPoolSize(5);   // Maximum number of threads
        executor.setQueueCapacity(10); // Queue capacity before rejecting tasks
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "executorService")
    public ExecutorService executorService() {
        // Configure a thread pool with a fixed number of threads
        return Executors.newFixedThreadPool(3);
    }
}