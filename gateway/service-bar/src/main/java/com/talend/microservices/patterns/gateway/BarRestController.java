package com.talend.microservices.patterns.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/bar", produces = "application/json")
public class BarRestController {
    Logger logger = LoggerFactory.getLogger(BarRestController.class);

    @GetMapping("/hello")
    public String hello() {
        logger.info("Bar hello");
        return "Hello from bar";
    }
}
