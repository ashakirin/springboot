package com.talend.microservices.patterns.trace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class TracingCompositeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TracingCompositeServiceApplication.class, args);
    }

}
