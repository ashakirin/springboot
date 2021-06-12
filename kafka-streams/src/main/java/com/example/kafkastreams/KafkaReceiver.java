package com.example.kafkastreams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaReceiver {
    private Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);

    @KafkaListener(topics = "testTopic", groupId = "myGroup")
    public void listen(@Payload String payload, @Headers Map<Object, Object> headers) {
        LOGGER.info("Payload: " + payload);
    }
}
