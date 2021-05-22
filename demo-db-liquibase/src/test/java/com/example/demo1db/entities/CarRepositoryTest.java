package com.example.demo1db.entities;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CarRepositoryTest {
    public static final long ID = 2005L;
    public static final String TYPE = "Honda";
    @Autowired
    CarRepository carRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        jdbcTemplate.update("insert into Car (id, type, ready) values (?, ?, ?)", ID, TYPE, true);
    }

    @AfterEach
    public void cleanup() {
        jdbcTemplate.update("delete from Car where id=?", ID);
    }

    @Test
    public void getCar() {
        Optional<Car> car = carRepository.findById(ID);
        assertThat(car.isPresent()).isTrue();
        assertThat(car.get().getType()).isEqualTo(TYPE);
    }
}