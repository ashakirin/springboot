package com.example.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.db.entity")
public class DemoDbApplicationEm {

    public static void main(String[] args) {
        SpringApplication.run(DemoDbApplicationEm.class, args);
    }

}
