package com.example.async.demo.reactive.model;

import lombok.Data;

@Data
public class ClickEvent {
    private String id;
    private String userId;
    private String eventData;
}
