package com.example.db.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class TestInner {

    @Repository
    public interface TestInnerRepo extends CrudRepository<BookEntity, Long> {};

    @Autowired
    private TestInnerRepo repo;
}
