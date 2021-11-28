package com.example.redisdata;

import com.example.redisdata.data.Car;
import com.example.redisdata.data.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PutMapping("/lists")
    public void lists() {
        List<String> test = Arrays.asList("1", "2", "3", "4");
//        template.opsForList().rightPushAll("mylist", test);
//        System.out.println(template.opsForList().size("mylist"));
//        System.out.println(template.opsForList().range("mylist", 0, 8));
        template.opsForHash().put("test", "map", test.toString());
        template.opsForHash().put("test", "isAdmin", "true");
        Map<Object, Object> map = template.opsForHash().entries("test");
        System.out.println(map.get("map")); // [1, 2, 3, 4]
        System.out.println(map.get("map") instanceof String); // true
        System.out.println(template.opsForHash().entries("test")); // {a=[1, 2, 3, 4], isAdmin=true}

//        long size = template.opsForList().size("mylist");
//        for (int i = 0; i < size; i++) {
//            final String s = template.opsForList().leftPop("mylist");
//            System.out.println(i + " - " + s);
//            System.out.println(template.opsForList().range("mylist", 0, 8));
//        }

    }

    @GetMapping("/scan")
    public Object scan() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        List<String> list2 = new ArrayList<>();
        list2.add("5");
        list2.add("6");
        list2.add("7");
        list2.add("8");
        List<String> list3 = new ArrayList<>();
        list3.add("9");
        list3.add("10");
        list3.add("11");
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("map1_test", list.toString());
        valueMap.put("SCAN_map2", list2.toString());
        valueMap.put("map3", list3.toString());
        template.opsForHash().putAll("test", valueMap); // {SCAN_map2=[5, 6, 7, 8], map3=[9, 10, 11, 12], map1=[1, 2, 3, 4]}
//        Cursor<Map.Entry<Object, Object>> cursor = template.opsForHash().scan("test", ScanOptions.scanOptions().match("*test").build());
        Cursor<Map.Entry<Object, Object>> cursor = template.opsForHash().scan("test", ScanOptions.scanOptions().match("*test").count(3).build());
        if (cursor.hasNext()) {
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                System.out.println(entry.getValue()); // [5, 6, 7, 8]
                return entry.getValue();
            }
        }
        return "";
    }


    @GetMapping("/cars/{id}")
    public Car setValue(@PathVariable("id") Long id) {
        //template.opsForValue().set(key, value);
        return carRepository.findById(id).get();
    }
}
