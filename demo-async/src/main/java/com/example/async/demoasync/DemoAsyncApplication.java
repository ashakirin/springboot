package com.example.async.demoasync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.example.async.demoasync")
@EnableAsync
public class DemoAsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoAsyncApplication.class, args);
    }

}
