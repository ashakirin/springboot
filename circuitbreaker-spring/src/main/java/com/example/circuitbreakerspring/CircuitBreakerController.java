package com.example.circuitbreakerspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class CircuitBreakerController {
    private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
    private final CircuitBreakerFactory cbFactory;
    private final RestTemplate restTemplate;
    private final WebClient webClient = WebClient.create("http://localhost:8080");


    @Autowired
    public CircuitBreakerController(CircuitBreakerFactory cbFactory) {
        this.cbFactory = cbFactory;
        restTemplate = (new RestTemplateBuilder()).rootUri("http://localhost:8080").build();
    }

    @GetMapping("/invoke")
    public String invoke() {
        final CircuitBreaker cb = cbFactory.create("my circuite breaker");
        String result = cb.run(() -> {
            logger.info("Current threa: " + Thread.currentThread());
            return restTemplate.getForObject("/react", String.class);
        }, throwable -> {
            logger.error("Error: " + throwable);
            return "error response";
        });

        return result;
    }

    @GetMapping("/reactive")
    public Mono<String> invokeRecative() {
        return webClient.get().uri("/react").retrieve().bodyToMono(String.class)
                .transform(it -> cbFactory.create("slow").run(() -> it, throwable -> Mono.just("fallback")));
    }

}
