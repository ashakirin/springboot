package com.example.async.demo.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserClicks {
    private User user;
    private List<ClickEvent> events;
}
