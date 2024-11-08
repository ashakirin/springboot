package com.example.async.demo.reactive;

import com.example.async.demo.reactive.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Flux<User> findByUserGroup(String userGroup);
}
