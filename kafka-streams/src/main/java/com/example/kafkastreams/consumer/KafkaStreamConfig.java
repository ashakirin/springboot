package com.example.kafkastreams.consumer;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaStreamConfig {
    @Bean
    public java.util.function.Consumer<KStream<String, String>> process() {
        return stream -> stream.foreach((key, value) -> {
            System.out.println(key + ":" + value);
        });
    }
}
