package com.example.demorabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class RabbitMQConfiguration {

    public static final String TEST_QUEUE = "TestQueue";
    public static final String FIRST_QUEUE = "FirstQueue";
    public static final String SECOND_QUEUE = "SecondQueue";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String HEADERS_EXCHANGE = "headersExchange";
    public static final String HEADERS_QUEUE = "HeadersQueue";
    public static final String SI_OUT_QUEUE = "si.out.queue";

    @Bean
    Queue getQueue() {
        return new Queue(TEST_QUEUE, false);
    }

    @Bean
    @Qualifier("firstQueue")
    Queue getFirstQueue() {
        return new Queue(FIRST_QUEUE, false);
    }

    @Bean
    @Qualifier("secondQueue")
    Queue getSecondQueue() {
        return new Queue(SECOND_QUEUE, false);
    }

    @Bean
    @Qualifier("headersQueue")
    Queue getHeadersQueue() {
        return new Queue(HEADERS_QUEUE, false);
    }

    @Bean
    @Qualifier("si.out.queue")
    Queue getSIOutQueue() {
        return new Queue(SI_OUT_QUEUE, false);
    }

    @Bean
    public FanoutExchange sampleExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public TopicExchange topicExchangeExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADERS_EXCHANGE);
    }

    @Bean
    public Binding firstBinding(@Qualifier("firstQueue") Queue firstQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(firstQueue).to(fanoutExchange);
    }

    @Bean
    public Binding secondBinding(@Qualifier("secondQueue") Queue secondQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(secondQueue).to(fanoutExchange);
    }

    @Bean
    public Binding headersBinding(@Qualifier("headersQueue") Queue headersQueue, HeadersExchange headersExchange) {
        return BindingBuilder.bind(headersQueue).to(headersExchange).where("my-header").matches("my-value");
    }

    @Bean
    public Binding topicBindingFirst(@Qualifier("firstQueue") Queue firstQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(firstQueue).to(topicExchange).with("queue.*");
    }

    @Bean
    public Binding topicBindingSecond(@Qualifier("secondQueue") Queue secondQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(secondQueue).to(topicExchange).with("queue.*");
    }
}
