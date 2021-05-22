package com.example.demo1db.entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {
    Optional<Car> findByType(String type);

    @Query("select c from Car c where c.ready=:ready")
    List<Car> findReady(@Param("ready") boolean ready);
}
