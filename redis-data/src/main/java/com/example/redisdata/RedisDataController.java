package com.example.redisdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisDataController {
    @Autowired
    private RedisTemplate<String, String> template;

    @GetMapping("/{key}")
    public String getValue(@PathVariable("key") final String key) {
        //return template.opsForValue().get(key);
        return template.opsForList().index(key, 0);
    }

    @PutMapping("/{key}")
    public void setValue(@PathVariable("key") final String key, @RequestParam String value) {
        //template.opsForValue().set(key, value);
        template.opsForList().leftPush(key, value);
    }
}
