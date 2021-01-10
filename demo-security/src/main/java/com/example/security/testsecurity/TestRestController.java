package com.example.security.testsecurity;

import java.security.Principal;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/")
    public String testGetMethod(final Principal principal) {
        return "Hello " + principal.getName() + " ;" + SecurityContextHolder.getContext().getAuthentication();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin")
    public String testAdminGetMethod() {
        return "Hello admin";
    }
}
