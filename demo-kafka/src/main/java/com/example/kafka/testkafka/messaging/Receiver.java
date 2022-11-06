package com.example.kafka.testkafka.messaging;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @KafkaListener(topics = "test")
    public void listen(@Payload String payload, @Headers Map<Object, Object> headers) {
        LOGGER.info("Payload: " + payload);
    }

//    @KafkaListener(topics = "testobject")
//    public void listenObject(@Payload Foo payload, @Headers Map<Object, Object> headers) {
//        LOGGER.info("Payload: " + payload);
//    }
}
