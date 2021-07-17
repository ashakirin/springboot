package com.talend.microservices.patterns.democompositeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class DemoCompositeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoCompositeServiceApplication.class, args);
    }

}
