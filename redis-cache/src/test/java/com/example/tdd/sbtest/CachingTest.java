package com.example.tdd.sbtest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class CachingTest {
    @MockBean
    private CarRepository carRepository;

    @Autowired
    private CarService carService;

    @Test
    public void caching() {
        given(carRepository.findByName(anyString())).willReturn(Optional.of(new Car("test name", "test type")));

        Car car = carService.getCarDetails("test name");
        car = carService.getCarDetails("test name");

        Assertions.assertThat(car.getName()).isEqualTo("test name");
        verify(carRepository, times(1)).findByName(anyString());
    }

}
