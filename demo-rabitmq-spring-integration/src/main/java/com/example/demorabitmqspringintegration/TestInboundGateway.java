package com.example.demorabitmqspringintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import reactor.core.publisher.Flux;

@Configuration
public class TestInboundGateway {
    @Bean
    public IntegrationFlow inboundGatewayHelloword1() {
        return IntegrationFlows.from(
                Http.inboundGateway("/helloword")
                        .requestMapping(r -> r.methods(HttpMethod.GET))
                        .mappedResponseHeaders("*"))
                .log()
                .handle((p, h) -> "{\"message\": \"Hello worldXXX\"}")
                .enrichHeaders(h -> h.header("TestProperty", "testValue"))
                .get();
    }

    @Bean
    public IntegrationFlow inboundGatewayHelloword2() {
        return IntegrationFlows.from(
                        Http.inboundGateway("/helloword2"))
                .transform(new ObjectToStringTransformer())
                .log()
                .handle((p, h) -> p)
                .get();
    }

    @Bean
    public IntegrationFlow processGateway1() {
        return flow -> flow
                .log()
                .transform(new GenericTransformer() {
                    @Override
                    public Object transform(Object source) {
                        return "Test1 " + source;
                    }
                });
    }

    @Bean
    public IntegrationFlow processGateway2() {
        return flow -> flow
                .log()
                .transform(s -> "Test2: " + s);
    }
}
