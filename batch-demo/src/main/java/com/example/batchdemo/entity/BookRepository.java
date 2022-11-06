package com.example.batchdemo.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface BookRepository extends PagingAndSortingRepository<BookEntity, Long> {
    Optional<BookEntity> findByName(final String name);
}
