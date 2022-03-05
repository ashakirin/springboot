package com.example.demorabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerRabbitMQ {
    private static Logger LOGGER = LoggerFactory.getLogger(RestControllerRabbitMQ.class);
    private MessagingService messagingService;

    public RestControllerRabbitMQ(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @PostMapping("/message")
    public void publishMessage(@RequestBody String message) {
        LOGGER.info(message);
        messagingService.sendMessage(message);
    }
}
