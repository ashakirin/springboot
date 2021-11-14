package com.example.demorabbitmq2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RabbitMQService {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQService.class);
    private final RabbitTemplate template;

    @Autowired
    public RabbitMQService(RabbitTemplate template) {
        this.template = template;
    }

    public void send(String testMessage) {
        logger.info("Sending message to test routing key");
        template.convertAndSend(RabbitMQConfiguration.TEST_TOPIC_EXCHANGE, RabbitMQConfiguration.TEST_ROUTING_KEY, testMessage);

//        Message message = new Message("test".getBytes(StandardCharsets.UTF_8));
//        template.send(RabbitMQConfiguration.TEST_QUEUE, message);
    }

    @RabbitListener(queues = RabbitMQConfiguration.TEST_QUEUE)
    public void receive(Message message) {
        logger.info("!!! received !!!: " + message.getBody());
//        throw new AmqpRejectAndDontRequeueException("test");
        throw new IllegalStateException("test");
    }

    @RabbitListener(queues = RabbitMQConfiguration.DEAD_LETTER_QUEUE)
    public void receiveDlq(Message message) {
        logger.info("!!! received in DLQ !!!: " + message.getBody());
    }
}
