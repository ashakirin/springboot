package com.example.demo1db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class DBController {

    private final DBService dbService;

    //    @Autowired
    public DBController(DBService dbService) {
        this.dbService = dbService;
    }

    @GetMapping("cars/{type}")
    public CarDTO getCar(@PathVariable String type) {
        Optional<CarDTO> car = dbService.findCar(type);
        return car
                .map(c -> c)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource"));
    }

    @GetMapping("cars")
    public List<CarDTO> findReadyCar(@RequestParam boolean ready) {
        return dbService.findReadyCars(ready);
    }
}
