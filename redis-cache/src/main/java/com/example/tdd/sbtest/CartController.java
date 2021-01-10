package com.example.tdd.sbtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
public class CartController {

    private CarService carService;

    public CartController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/car/{name}")
    public Car getCart(@PathVariable("name") String name) {
        return carService.getCarDetails(name);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void carNotFoundHanlder(CarNotFoundException exception) {

    }
}
