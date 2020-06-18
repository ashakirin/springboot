package com.talend.microservices.patterns.trace;

import com.talend.microservices.patterns.trace.kafka.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (path = "basic")
public class BasicServiceController {
    Logger LOGGER = LoggerFactory.getLogger(BasicServiceController.class);
    @Autowired
    private Sender sender;

    @GetMapping("/hello")
    String helloOperation() {
        LOGGER.info("!!! Received get request");
        return "Hello from basic service";
    }

    @GetMapping("/send")
    String sendOperation() {
        LOGGER.info("!!! Recieved send request");
        sender.send("basic test");
        return "Hello from basic service";
    }
}
