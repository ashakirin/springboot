package com.example.democontroller.repo;

import com.example.democontroller.repo.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.awt.print.Book;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void shouldCreateBookEntity() {
        BookEntity bookEntity = bookRepository.saveAndFlush(BookEntity.builder()
                .title("Book1")
                .author("Author1")
                .build());
        assertThat(bookEntity.getId()).isNotNull();

        Optional<BookEntity> readBookEntity = bookRepository.findById(bookEntity.getId());
        assertThat(readBookEntity).isNotEmpty();
        assertThat(readBookEntity.get().getTitle()).isEqualTo("Book1");
    }
}