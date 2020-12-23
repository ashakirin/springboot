package com.talend.microservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/bar", produces = "application/json")
public class BarRestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from bar";
    }
}
