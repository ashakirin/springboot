package com.talend.microservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/qux", produces = "application/json")
public class QuxRestController {

    @GetMapping("/hello")
    public String fooHello() {
        return "Hello from qux";
    }
}
