package com.talend.microservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/foo", produces = "application/json")
public class FooRestController {

    @GetMapping("/hello")
    public String fooHello() {
        return "Hello from foo";
    }
}
