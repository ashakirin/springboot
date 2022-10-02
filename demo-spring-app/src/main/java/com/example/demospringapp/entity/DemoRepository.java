package com.example.demospringapp.entity;

import org.springframework.data.repository.CrudRepository;

public interface DemoRepository extends CrudRepository<DemoEntity, Long> {

    DemoEntity findByContent(String content);
}
