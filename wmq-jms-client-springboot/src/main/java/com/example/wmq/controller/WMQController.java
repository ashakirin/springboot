package com.example.wmq.controller;

import com.example.wmq.jms.WMQListener;
import com.example.wmq.jms.WMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WMQController {
    private final WMQSender wmqClient;
    private final WMQListener wmqListener;

    @Autowired
    public WMQController(WMQSender wmqClient, WMQListener wmqListener) {
        this.wmqClient = wmqClient;
        this.wmqListener = wmqListener;

        wmqListener.listenForMessage(m -> {
            System.out.println("Received message: " + m);
        });
    }

    @GetMapping("/send")
    public String sendMessage() {
        wmqClient.sendMessage();
        return "hello";
    }
}
