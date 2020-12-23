package com.example.dbmongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.example.dbmongo.entity")
@EntityScan("com.example.dbmongo.entity")
public class DbMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbMongoApplication.class, args);
    }

}
