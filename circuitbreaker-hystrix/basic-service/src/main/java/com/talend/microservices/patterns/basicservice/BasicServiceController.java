package com.talend.microservices.patterns.basicservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "basic")
public class BasicServiceController {

    @GetMapping("/hello")
    String helloOperation() {
        return "Hello from basic service";
    }
}
