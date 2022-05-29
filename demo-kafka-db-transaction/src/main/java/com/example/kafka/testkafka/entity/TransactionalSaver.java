package com.example.kafka.testkafka.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionalSaver {
    @Autowired
    private BookRepository repo;

   @Transactional("chainedTxM")
    public void save(String in) {
        this.repo.save(new Book(in));
        throw new RuntimeException("foo");
    }
}
