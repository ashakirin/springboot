package com.example.db.entity;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface BookRepository extends CrudRepository<BookEntity, Integer> {
    Optional<BookEntity> findByName(final String name);
}
