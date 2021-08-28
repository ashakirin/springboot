package com.talend.microservices.patterns.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayRestController {
    Logger logger = LoggerFactory.getLogger(GatewayRestController.class);

    @GetMapping("/fallback")
    public String getRequest() {
        logger.info("Invoked callback");
        return "fallback";
    }
}
