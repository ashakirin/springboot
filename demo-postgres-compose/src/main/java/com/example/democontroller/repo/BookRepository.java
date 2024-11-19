package com.example.democontroller.repo;

import com.example.democontroller.repo.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query(value = "select b.* from books b where b.search_vector @@ to_tsquery('english', ?1)", nativeQuery = true)
    List<BookEntity> fullTextSearch(String text);
}
