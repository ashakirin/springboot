package com.example.demorabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class RabbitMQConfiguration {

    public static final String FIRST_QUEUE = "FirstQueue";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String HEADERS_QUEUE = "HeadersQueue";

    @Bean
    @Qualifier("firstQueue")
    Queue getFirstQueue() {
        return new Queue(FIRST_QUEUE, false);
    }

    @Bean
    public TopicExchange topicExchangeExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBindingFirst(@Qualifier("firstQueue") Queue firstQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(firstQueue).to(topicExchange).with("queue.*");
    }
}
