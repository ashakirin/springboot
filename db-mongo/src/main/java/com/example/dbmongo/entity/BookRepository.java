package com.example.dbmongo.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, Integer> {

    @Query("{'name' : ?0}")
    List<Book> findByName(String name);
}
