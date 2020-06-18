package com.talend.microservices.patterns.trace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/send")
    public String invokeSend() {
        return basicServiceClient.invokeSendTopic();
    }
}
