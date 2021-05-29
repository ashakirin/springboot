package com.example.db.entity;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
public interface AuthorRepository extends CrudRepository<AuthorEntity, Integer> {
}
