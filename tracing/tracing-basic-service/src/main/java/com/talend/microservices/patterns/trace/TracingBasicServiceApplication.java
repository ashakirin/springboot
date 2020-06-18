package com.talend.microservices.patterns.trace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TracingBasicServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TracingBasicServiceApplication.class, args);
    }

}
