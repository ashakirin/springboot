package com.example.kafka.testkafka;

import com.example.kafka.testkafka.messaging.Sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @Autowired
    private Sender sender;

    @GetMapping("/send")
    public void sendIntoTopic() {
        sender.send("test");
    }
}
