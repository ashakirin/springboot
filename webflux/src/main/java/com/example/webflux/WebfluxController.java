package com.example.webflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class WebfluxController {
    private static Logger logger = LoggerFactory.getLogger(WebfluxController.class);
    WebClient client = WebClient.create("http://localhost:8080");

    @GetMapping("/hello")
    public Mono<Employee> hello() {
        Mono<Employee> response = client.get()
                .uri("/react")
                .retrieve()
                .onStatus(s -> s.equals(HttpStatus.NOT_FOUND), cr -> Mono.just(new RuntimeException("Not found response")))
                .bodyToMono(Employee.class);

        return response
                .log("INFO");
    }
}
