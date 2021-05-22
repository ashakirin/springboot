package com.example.demo1db;

import com.example.demo1db.entities.Car;
import com.example.demo1db.entities.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DBServiceTest {
    public static final String MY_TYPE = "MY_TYPE";
    @Mock
    CarRepository carRepository;

    @InjectMocks
    DBService dbService;

    @Test
    public void findCar() {
        // Arrange
        Car car = new Car(DBServiceTest.MY_TYPE);
        when(carRepository.findByType(MY_TYPE)).thenReturn(Optional.of(car));

        // Act
        Optional<CarDTO> carDTO = dbService.findCar(DBServiceTest.MY_TYPE);

        // Assert
        assertThat(carDTO.get().type).isEqualTo(DBServiceTest.MY_TYPE);
    }
}