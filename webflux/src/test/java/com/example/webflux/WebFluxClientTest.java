package com.example.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebFluxClientTest {
    @Autowired
    WebFluxClient webFluxClient;

    @Test
    public void clientTest() {
        webFluxClient.retrieveString();
    }

}