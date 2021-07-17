package com.example.service.bar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello bar";
    }
}
