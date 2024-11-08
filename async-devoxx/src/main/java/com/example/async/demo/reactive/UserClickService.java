package com.example.async.demo.reactive;

import com.example.async.demo.reactive.model.ClickEvent;
import com.example.async.demo.reactive.model.User;
import com.example.async.demo.reactive.model.UserClicks;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UserClickService {

    private final UserRepository userRepository;
    private final ClickEventService clickEventService;

    public UserClickService(UserRepository userRepository, ClickEventService clickEventService) {
        this.userRepository = userRepository;
        this.clickEventService = clickEventService;
    }

    public Flux<UserClicks> getUserGroupClicks(String userGroup) {
        Flux<User> users = userRepository.findByUserGroup(userGroup);
        Flux<ClickEvent> clickEvents = clickEventService.getClickEvents();

        return users.flatMap(user ->
                clickEvents
                        .filter(ce -> ce.getUserId().equals(user.getId()))
                        .collectList()
                        .map(clicks -> new UserClicks(user, clicks))
        );
    }
}
