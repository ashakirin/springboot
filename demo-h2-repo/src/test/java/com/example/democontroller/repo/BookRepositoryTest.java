package com.example.democontroller.repo;

import com.example.democontroller.repo.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void shouldCreateBookEntity() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.id = 1L;
        BookEntity createdBookEntity = bookRepository.saveAndFlush(bookEntity);
        assertThat(createdBookEntity.id).isNotNull();

        Optional<BookEntity> readBookEntity = bookRepository.findById(bookEntity.id);
        assertThat(readBookEntity).isNotEmpty();
    }
}