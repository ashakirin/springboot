package com.example.db.data;

import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface BookRepository extends CrudRepository<BookEntity, Long> {
    Optional<BookEntity> findByName(String name);
}
