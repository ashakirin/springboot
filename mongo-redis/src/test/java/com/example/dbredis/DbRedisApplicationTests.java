package com.example.dbredis;

import com.example.dbredis.entity.TestDocument;
import com.example.dbredis.entity.TestDocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class DbRedisApplicationTests {
    @Autowired
    DbRedisService dbRedisService;

    @Test
    void contextLoads() {
    }

}
