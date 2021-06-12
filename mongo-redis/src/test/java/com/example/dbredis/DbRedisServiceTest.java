package com.example.dbredis;

import com.example.dbredis.entity.TestDocument;
import com.example.dbredis.entity.TestDocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({DbRedisService.class})
class DbRedisServiceTest {
    @Autowired
    DbRedisService dbRedisService;

    @MockBean
    RedisTemplate<String, TestDocument> redisTemplate;

    @MockBean
    TestDocumentRepository testDocumentRepository;

    @Mock
    ValueOperations<String, TestDocument> valueOperations;

    @Test
    void shouldGetEntityFromCache() {
        // Arrange
        TestDocument testDocument = new TestDocument();
        testDocument.id = "testId";

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("testId")).thenReturn(testDocument);
        when(testDocumentRepository.findById("testId")).thenReturn(Optional.of(testDocument));

        // Act
        TestDocument testDocument1 = dbRedisService.getDocument("testId");

        // Verify
        assertEquals("testId", testDocument1.id);
    }
}