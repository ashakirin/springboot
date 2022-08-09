package com.example.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.db.entity")
@EnableJpaRepositories(considerNestedRepositories=true)
public class DemoDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoDbApplication.class, args);
    }

}
