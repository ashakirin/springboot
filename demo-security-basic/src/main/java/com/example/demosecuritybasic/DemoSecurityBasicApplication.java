package com.example.demosecuritybasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true)
public class DemoSecurityBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSecurityBasicApplication.class, args);
    }

}
