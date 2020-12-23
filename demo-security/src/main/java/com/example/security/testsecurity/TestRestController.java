package com.example.security.testsecurity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/")
    public String testGetMethod() {
        return "Hello";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin")
    public String testAdminGetMethod() {
        return "Hello admin";
    }
}
