package com.example.mongock;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableMongock
public class MongockApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongockApplication.class, args);
    }

}
