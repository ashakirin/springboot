package com.example.demorabbitmq;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfiguration {

    public static final String TEST_QUEUE = "TestQueue";

    @Bean
    Queue getQueue() {
        return new Queue(TEST_QUEUE, false);
    }
}
