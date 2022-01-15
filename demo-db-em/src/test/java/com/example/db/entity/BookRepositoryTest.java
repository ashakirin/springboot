package com.example.db.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        jdbcTemplate.update("insert into Book(id, name) values (?, ?)", 01L, "testName");
    }

    @AfterEach
    public void shutdown() {
        jdbcTemplate.update("delete from Book where id = ?", 01L);
    }

    @Test
    void shouldFindByName() {
        Optional<BookEntity> bookEntity = bookRepository.findByName("testName");
        assertTrue(bookEntity.isPresent());
        assertEquals(01L, bookEntity.get().getId());
    }
}