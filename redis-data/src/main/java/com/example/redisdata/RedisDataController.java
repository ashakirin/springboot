package com.example.redisdata;

import com.example.redisdata.data.Car;
import com.example.redisdata.data.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedisDataController {
    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/keys/{key}")
    public String getValue(@PathVariable("key") final String key) {
        //return template.opsForValue().get(key);
        return template.opsForList().index(key, 0);
    }

    @PutMapping("/keys/{key}")
    public void setValue(@PathVariable("key") final String key, @RequestParam String value) {
        //template.opsForValue().set(key, value);
        template.opsForList().leftPush(key, value);
    }

    @PostMapping("/cars")
    public void setValue() {
        //template.opsForValue().set(key, value);
        Car car = new Car(1L, "my name", "my type");
        carRepository.save(car);
    }

    @GetMapping("/cars/{id}")
    public Car setValue(@PathVariable("id") Long id) {
        //template.opsForValue().set(key, value);
        return carRepository.findById(id).get();
    }
}
