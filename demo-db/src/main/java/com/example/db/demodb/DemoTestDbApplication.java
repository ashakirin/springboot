package com.example.db.demodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.db.data")
@EntityScan("com.example.db.data")
public class DemoTestDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoTestDbApplication.class, args);
    }

}
