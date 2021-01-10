package com.example.tdd.sbtest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CarRepository extends CrudRepository<Car, Long> {
    Optional<Car> findByName(String name);
}
