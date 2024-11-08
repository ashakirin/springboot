package com.example.async.demo.reactive;

import com.example.async.demo.reactive.model.UserClicks;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class UserClickController {
    private final UserClickService userClickService;

    public UserClickController(UserClickService userClickService) {
        this.userClickService = userClickService;
    }

    @GetMapping("/user-groups/{userGroupId}/clicks")
    public Flux<UserClicks> getUserClicks(@PathVariable String userGroupId) {
        return userClickService.getUserGroupClicks(userGroupId);
    }
}
