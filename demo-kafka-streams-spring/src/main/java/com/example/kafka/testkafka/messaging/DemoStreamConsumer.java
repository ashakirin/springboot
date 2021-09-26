package com.example.kafka.testkafka.messaging;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoStreamConsumer {
//    @Bean
//    public java.util.function.Consumer<KStream<String, String>> process() {
//        return input ->
//                input
//                        .mapValues(v -> v + "_suffix")
//                        .foreach((key, value) -> {
//                    System.out.println("Key: " + key + " Value: " + value);
//                });
//    }

    @Bean
    public java.util.function.Consumer<KStream<String, Foo>> process() {
        return input ->
                input
                        .foreach((key, value) -> {
                            System.out.println("Key: " + key + " Value: " + value);
                        });
    }
}
