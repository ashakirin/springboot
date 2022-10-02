package com.example.demospringapp.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DemoRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DemoRepository demoRepository;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("INSERT INTO DEMO_ENTITY (Id, Content) VALUES (0, 'test')");
    }

    @Test
    void shouldReturnById() {
        Optional<DemoEntity> result = demoRepository.findById(0L);
        assertThat(result).isPresent();
        DemoEntity entity = result.get();
        assertThat(entity.getContent()).isEqualTo("test");
    }

}
