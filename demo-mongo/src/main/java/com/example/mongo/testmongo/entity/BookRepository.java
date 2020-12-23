package com.example.mongo.testmongo.entity;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<BookEntity, Integer> {
    Optional<BookEntity> findByName(String bookName);
}
