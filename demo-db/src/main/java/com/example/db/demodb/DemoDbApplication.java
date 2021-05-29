package com.example.db.demodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.db.entity")
//@ComponentScan(basePackages = {"com.example.db.entity", "com.example.db.demodb"})
@EnableJpaRepositories(basePackages = "com.example.db.entity")
public class DemoDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoDbApplication.class, args);
    }

}
