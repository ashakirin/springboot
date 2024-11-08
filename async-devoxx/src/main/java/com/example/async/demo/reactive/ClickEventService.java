package com.example.async.demo.reactive;

import com.example.async.demo.reactive.model.ClickEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ClickEventService {

    public Flux<ClickEvent> getClickEvents() {
        return null;
    }
}
