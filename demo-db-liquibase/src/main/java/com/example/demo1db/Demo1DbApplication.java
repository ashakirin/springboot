package com.example.demo1db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.demo1db.entities")
public class Demo1DbApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo1DbApplication.class, args);
    }

}
