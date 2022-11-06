package com.example.batchdemo.entity;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<AuthorEntity, Integer> {
}
