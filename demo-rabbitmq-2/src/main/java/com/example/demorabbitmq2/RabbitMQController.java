package com.example.demorabbitmq2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {
    private final RabbitMQService service;

    @Autowired
    public RabbitMQController(RabbitMQService service) {
        this.service = service;
    }

    @GetMapping("/send")
    public String send() {
        service.send("Test message");
        return "OK";
    }
}
