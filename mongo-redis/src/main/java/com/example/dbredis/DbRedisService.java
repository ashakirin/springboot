package com.example.dbredis;

import com.example.dbredis.entity.TestDocument;
import com.example.dbredis.entity.TestDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Component
public class DbRedisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbRedisService.class);
    private TestDocumentRepository testDocumentRepository;
    private RedisTemplate<String, TestDocument> redisTemplate;

    @Autowired
    public DbRedisService(TestDocumentRepository testDocumentRepository, RedisTemplate<String, TestDocument> redisTemplate) {
        this.testDocumentRepository = testDocumentRepository;
        this.redisTemplate = redisTemplate;
    }

    public TestDocument getDocument(@PathVariable String id) {
        TestDocument testDocument = redisTemplate.opsForValue().get(id);
        LOGGER.warn((testDocument != null) ? testDocument.toString() : "not found");
        LOGGER.info("Reading from repository: " + id);
        return testDocumentRepository.findById(id).orElse(null);
    }

    public TestDocument createDocument() {
        TestDocument testDocument = new TestDocument();
        testDocument.id = UUID.randomUUID().toString();
        testDocument.description = "testDescription";
        testDocument.name = "testName";
        testDocument.volume = 2;
        redisTemplate.opsForValue().set(testDocument.id, testDocument);
        return testDocumentRepository.save(testDocument);
    }

    public void addToList() {
        TestDocument testDocument = new TestDocument();
        testDocument.id = UUID.randomUUID().toString();
        testDocument.description = "testDescription";
        testDocument.name = "listDocument";
        testDocument.volume = 1;
        redisTemplate.boundListOps("testList").leftPush(testDocument);
    }

    public void printFromList() {
        while (true) {
            TestDocument testDocument = redisTemplate.boundListOps("testList").rightPop();
            if (testDocument == null) {
                break;
            }
            LOGGER.info(testDocument.name);
        }
    }
}
