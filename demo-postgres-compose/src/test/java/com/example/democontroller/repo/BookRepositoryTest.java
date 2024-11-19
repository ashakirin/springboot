package com.example.democontroller.repo;

import com.example.democontroller.repo.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.properties.TestcontainersPropertySourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({TestContainerConfiguration.class, TestcontainersPropertySourceAutoConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    @Test
    void fullTextSearch() {
        BookEntity bookEntity = bookRepository.saveAndFlush(BookEntity.builder()
                .title("My dream book")
                .author("Author1")
                .build());
        List<BookEntity> dream = bookRepository.fullTextSearch("dream");

        assertThat(dream.size()).isEqualTo(1);
    }
}