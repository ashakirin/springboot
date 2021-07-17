package com.example.service.foo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FooService {
    RestTemplate restTemplate;

    public FooService() {
        this.restTemplate = new RestTemplate();
    }

    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name="CB_ANNOTATED")
    @io.github.resilience4j.timelimiter.annotation.TimeLimiter(name="TL_ANNOTATED")
    public String invokeRemoteBar() {
//        return restTemplate.getForObject("http://google.com:8091/hello", String.class);
        return restTemplate.getForObject("http://localhost:8091/hello", String.class);
    }

}
