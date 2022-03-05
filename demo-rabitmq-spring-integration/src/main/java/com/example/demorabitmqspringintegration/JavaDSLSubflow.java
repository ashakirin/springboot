package com.example.demorabitmqspringintegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.transformer.ObjectToStringTransformer;

@Configuration
public class JavaDSLSubflow {
//    @Autowired
//    IntegrationFlow logging;
//
//    @Bean
//    public IntegrationFlow firstFlow() {
//        return IntegrationFlows.from(
//                        Http.inboundChannelAdapter("/subflows"))
//                .transform(new ObjectToStringTransformer())
//                .gateway(logging)
//                .log()
//                .get();
//    }
//
//    @Bean
//    public IntegrationFlow testFlow1() {
//        return IntegrationFlows.from("")
//                .get();
//    }
//
//    @Bean
//    public IntegrationFlow testFlow2() {
//        return flow -> flow
//                .log();
//    }
}