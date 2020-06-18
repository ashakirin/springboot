package com.talend.microservices.patterns.trace.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

public class Receiver {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Receiver.class);

    @KafkaListener(topics = "test")
    public void receive(@Payload String payload, @Headers Map<Object, Object> headers) {
        LOGGER.info("received payload='{}'; headers='{}'", payload, headers);

    }
}
