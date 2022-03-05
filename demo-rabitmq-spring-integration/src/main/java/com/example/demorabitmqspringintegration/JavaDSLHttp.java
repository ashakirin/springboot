package com.example.demorabitmqspringintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.transformer.ObjectToStringTransformer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Configuration
public class JavaDSLHttp {
//    @Bean
//    public IntegrationFlow inbound() {
//        return IntegrationFlows.from(
//                        Http.inboundChannelAdapter("/test"))
//                .handle((p, h) -> p)
//                .transform(s -> ((String)s).getBytes(StandardCharsets.UTF_8))
//                .log()
//                .transform(new ObjectToStringTransformer())
//                .log()
//                .enrichHeaders(h -> h.header("TestProperty", "testValue"))
//                .get();
//    }
}