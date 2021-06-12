package com.example.kafkastreams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaStreamController {
    private KafkaService kafkaService;

    @Autowired
    public KafkaStreamController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody String text) {
        kafkaService.sendMessage(text);
    }
}
