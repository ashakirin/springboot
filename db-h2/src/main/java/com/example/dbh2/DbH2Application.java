package com.example.dbh2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.dbh2.entity")
@EntityScan("com.example.dbh2.entity")
public class DbH2Application {

    public static void main(String[] args) {
        SpringApplication.run(DbH2Application.class, args);
    }

}
