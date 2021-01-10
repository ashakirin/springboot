package com.example.tdd.sbtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SbtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbtestApplication.class, args);
    }

}
