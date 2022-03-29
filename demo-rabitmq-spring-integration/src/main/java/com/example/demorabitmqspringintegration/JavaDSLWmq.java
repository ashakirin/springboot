package com.example.demorabitmqspringintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.jms.dsl.Jms;

import javax.jms.ConnectionFactory;

@Configuration
public class JavaDSLWmq {
//    @Bean
//    public IntegrationFlow jmsInbound(ConnectionFactory connectionFactory) {
//        return IntegrationFlows.from(
//                        Jms.inboundAdapter(connectionFactory)
//                                .destination("DEV.QUEUE.1"))
////                .handle(m -> System.out.println(m.getPayload()))
//                .log()
//                .handle(Jms.outboundAdapter(connectionFactory).destination("DEV.QUEUE.2"))
//                .get();
//    }

//    @Bean
//    IntegrationFlow wmq_flow(javax.jms.ConnectionFactory connectionFactory) {
//        return IntegrationFlows.from(Jms.inboundAdapter(connectionFactory).destination("DEV.QUEUE.1"))
//                .log(LoggingHandler.Level.INFO)
//                .handle(Jms.outboundAdapter(connectionFactory).destination("DEV.QUEUE.2"))
//                .get();
//    }

}
