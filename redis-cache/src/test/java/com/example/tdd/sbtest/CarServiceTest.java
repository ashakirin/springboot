package com.example.tdd.sbtest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    public void test_findCar() {
        when(carRepository.findById(any())).thenReturn(Optional.of(new Car("test name", "test type")));

        Car car = carService.getCarDetails("test");

        Assertions.assertThat(car.getName()).isEqualTo("test name");
        Assertions.assertThat(car.getType()).isEqualTo("test type");
    }

    @Test
    public void test_notFoundCar() {
        when(carRepository.findById(any())).thenThrow(new CarNotFoundException());

        assertThrows(CarNotFoundException.class, () -> carService.getCarDetails("test"));
    }

}
