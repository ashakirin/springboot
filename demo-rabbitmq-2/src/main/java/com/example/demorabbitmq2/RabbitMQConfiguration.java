package com.example.demorabbitmq2;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String TEST_QUEUE = "TestQueue";
    public static final String TEST_TOPIC_EXCHANGE = "TestTopicExchange";
    public static final String TEST_ROUTING_KEY = "test routing key";
    public static final String DEAD_LETTER_QUEUE = "deadLetter.queue";
    public static final String DEAD_LETTER_EXCHANGE = "deadLetterExchange";

    @Bean
    @Qualifier("testQueue")
    public Queue getTestQueue() {
//        return QueueBuilder.nonDurable(TEST_QUEUE).withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
//                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE).build();
        return QueueBuilder.nonDurable(TEST_QUEUE).withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE).build();
    }

    @Bean
    @Qualifier(DEAD_LETTER_QUEUE)
    public Queue deadLetterQueue() {
        return QueueBuilder.nonDurable(DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public TopicExchange getTestTopicExchange() {
        return new TopicExchange(TEST_TOPIC_EXCHANGE);
    }

    @Bean
    public DirectExchange getDeadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Binding getTopicExchangeBinding(@Qualifier("testQueue") Queue testQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(testQueue).to(topicExchange).with(TEST_ROUTING_KEY);
    }

    @Bean
    public Binding deadLetterBinding(@Qualifier(DEAD_LETTER_QUEUE) Queue deadLetterQueue, DirectExchange dlqExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(dlqExchange).withQueueName();
    }
}
