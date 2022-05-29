package com.example.arch.archtestdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ArchtestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchtestDemoApplication.class, args);
    }

}
