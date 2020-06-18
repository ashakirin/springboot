package com.example.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class Receiver {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Receiver.class);

    @KafkaListener(topics = "test")
    public void receive(String payload) {
        LOGGER.info("received payload='{}'", payload);
    }
}
