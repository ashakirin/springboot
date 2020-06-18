package com.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class KafkaController {
    @Autowired
    private Sender sender;

    @GetMapping("/send")
    public void send() {
        sender.send("java test");
    }
}
