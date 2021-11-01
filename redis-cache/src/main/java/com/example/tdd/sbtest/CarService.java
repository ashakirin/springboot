package com.example.tdd.sbtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@CacheConfig(cacheNames = {"Car"})
public class CarService {
    CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Cacheable()
    public Car getCarDetails(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Optional<Car> car = carRepository.findByName(name);
        if (car == null) {
            throw new CarNotFoundException();
        }
        return car.get();
    }
}
