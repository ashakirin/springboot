package com.example.demo1db;

import com.example.demo1db.entities.Car;
import com.example.demo1db.entities.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DBService {
    private CarRepository carRepository;

    @Autowired
    public DBService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Optional<CarDTO> findCar(String type) {
        Optional<Car> carEntity = carRepository.findByType(type);
        return carEntity
                .map(c -> Optional.of(new CarDTO(c.getType(), c.ready)))
                .orElse(Optional.empty());
    }

    public List<CarDTO> findReadyCars(boolean ready) {
        return carRepository.findReady(ready).stream()
                .map(c -> new CarDTO(c.getType(), c.ready))
                .collect(Collectors.toList());
    }
}
