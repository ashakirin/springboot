package com.example.demorabitmqspringintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;

@Configuration
public class SubflowsConfiguration {
    @Bean
    public IntegrationFlow logging() {
        return flow -> flow.log().transform(s -> "SI Java DSL: " + s);
    }
}
