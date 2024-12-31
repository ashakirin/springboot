package com.example.democontroller.repo;

import com.example.democontroller.repo.entity.BookEntity;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyScrollableRepository extends JpaRepository<BookEntity, String> {
    Window<BookEntity> findByTitle(String title, ScrollPosition position);
    List<BookEntity> findByTitle(String title, PageRequest pageRequest);
    List<BookEntity> findByTitle(String title, Limit limit);
    Window<BookEntity> findByTitle(String title, ScrollPosition position, Limit limit);
}
