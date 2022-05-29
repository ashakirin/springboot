package com.example.demosecuritybasic;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoSecurityController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello")
    public String helloGet() {
        return "hello from security";
    }
}
