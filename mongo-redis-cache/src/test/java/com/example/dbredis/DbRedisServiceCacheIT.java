package com.example.dbredis;

import com.example.dbredis.entity.TestDocument;
import com.example.dbredis.entity.TestDocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DbRedisServiceCacheIT {
    public static final String TEST_ID = "testId2";
    @MockBean
    TestDocumentRepository testDocumentRepository;

    @Autowired
    DbRedisService dbRedisService;

    @Test
    void shouldGetEntityFromCache() {
        // Arrange
        TestDocument testDocument = new TestDocument();
        testDocument.id = TEST_ID;
        when(testDocumentRepository.findById(TEST_ID)).thenReturn(Optional.of(testDocument));

        // Act
        TestDocument testDocument1 = dbRedisService.getDocument(TEST_ID);
        TestDocument testDocument2 = dbRedisService.getDocument(TEST_ID);

        // Verify
        assertEquals(TEST_ID, testDocument1.id);
        assertEquals(TEST_ID, testDocument2.id);
        verify(testDocumentRepository, times(1)).findById(TEST_ID);
    }
}