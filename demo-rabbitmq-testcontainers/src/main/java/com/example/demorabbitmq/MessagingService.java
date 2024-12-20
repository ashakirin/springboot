package com.example.demorabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.demorabbitmq.RabbitMQConfiguration.FIRST_QUEUE;
import static com.example.demorabbitmq.RabbitMQConfiguration.TOPIC_EXCHANGE;

@Component
public class MessagingService {
    Logger LOGGER = LoggerFactory.getLogger(MessagingService.class);
    private RabbitTemplate rabbitTemplate;
    private List<String> messageList = new CopyOnWriteArrayList();

    @Autowired
    public MessagingService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(final String message) {
        MessageProperties props = new MessageProperties();
        props.getHeaders().put("my-header", "my-value");
        Message amqpMessage = new Message(message.getBytes(StandardCharsets.UTF_8), props);

        rabbitTemplate.send(TOPIC_EXCHANGE, "queue.first", amqpMessage);
    }

    @RabbitListener(queues = FIRST_QUEUE)
    public void consumeMessage(Message message) {
        LOGGER.info("Received message: " + new String(message.getBody()));
        LOGGER.info("Received headers: " + message.getMessageProperties().getHeaders());
        messageList.add(new String(message.getBody()));
    }


    public List<String> getMessageList() {
        return messageList;
    }
}
