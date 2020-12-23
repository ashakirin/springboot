package com.example.mongo.testmongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.example.mongo.testmongo.entity")
@EntityScan("com.example.mongo.testmongo.entity")
public class TestMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestMongoApplication.class, args);
	}

}
