package com.example.kafka.testkafka.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class Sender {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Foo> kafkaTemplateObject;

    public void send(String payload) {
        LOGGER.info("sending payload='{}'", payload);
        kafkaTemplate.send("test", UUID.randomUUID().toString(), payload);
    }

    public void sendInTransaction(String payload) {
        LOGGER.info("sending payload='{}'", payload);
        kafkaTemplate.executeInTransaction(t -> t.send("test", UUID.randomUUID().toString(), payload));
    }

    public void sendObject(String payload) {
        LOGGER.info("sending payload='{}'", payload);
        kafkaTemplateObject.send("testobject", UUID.randomUUID().toString(), new Foo(payload));
    }
}

