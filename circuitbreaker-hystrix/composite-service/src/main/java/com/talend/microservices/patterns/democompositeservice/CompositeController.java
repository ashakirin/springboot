package com.talend.microservices.patterns.democompositeservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/composite")
public class CompositeController {
    Logger LOGGER = LoggerFactory.getLogger(CompositeController.class);
    private final BasicServiceClient basicServiceClient;

    @Autowired
    public CompositeController(final BasicServiceClient basicServiceClient) {
        this.basicServiceClient = basicServiceClient;
    }

    @GetMapping("/hello")
    public String invokeHello() {
        return basicServiceClient.invokeBasic();
    }

}
