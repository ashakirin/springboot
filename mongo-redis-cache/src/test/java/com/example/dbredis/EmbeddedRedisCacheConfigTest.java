package com.example.dbredis;

import com.example.dbredis.entity.TestDocument;
import com.example.dbredis.entity.TestDocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@EnableCaching
@Import({DbRedisService.class, EmbeddedRedisConfiguration.class, RedisCacheConfiguration.class})
@ImportAutoConfiguration({CacheAutoConfiguration.class, RedisCacheConfiguration.class})
class EmbeddedRedisCacheConfigTest {
    public static final String TEST_ID = "testId";
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