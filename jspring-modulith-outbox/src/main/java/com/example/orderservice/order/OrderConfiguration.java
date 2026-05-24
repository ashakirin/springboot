package com.example.orderservice.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class OrderConfiguration {

    /** Override in tests to control time. */
    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }
}
