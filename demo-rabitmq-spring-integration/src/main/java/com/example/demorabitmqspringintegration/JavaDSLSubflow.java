package com.example.demorabitmqspringintegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.transformer.ObjectToStringTransformer;

@Configuration
public class JavaDSLSubflow {

    @Bean
    public IntegrationFlow firstFlow(@Autowired IntegrationFlow testFlow2) {
        return IntegrationFlows.from(
                        Http.inboundChannelAdapter("/subflows")).handle((p, h) -> p)
                .transform(new ObjectToStringTransformer())
                .gateway(testFlow2)
                .log()
                .get();
    }

    @Bean
    public IntegrationFlow testFlow2() {
//        return flow -> {
//            flow.log().transform(s -> "SI Java DSL: " + s);
//        };

        return flow -> flow.log(LoggingHandler.Level.INFO);
    }
}