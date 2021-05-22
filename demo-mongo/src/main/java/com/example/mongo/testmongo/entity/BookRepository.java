package com.example.mongo.testmongo.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookRepository extends MongoRepository<BookEntity, Integer> {
    List<BookEntity> findByName(String bookName);

    @Query("{ 'isbn' : ?0 }")
    List<BookEntity> findByISBN(String isbn);
}
