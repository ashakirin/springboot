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

    @Autowired
    public DbRedisService(TestDocumentRepository testDocumentRepository) {
        this.testDocumentRepository = testDocumentRepository;
    }

    @Cacheable("DocumentCache")
    public TestDocument getDocument(@PathVariable String id) {
        LOGGER.info("Reading from repository: " + id);
        return testDocumentRepository.findById(id).orElse(null);
    }

    public TestDocument createDocument() {
        TestDocument testDocument = new TestDocument();
        testDocument.id = UUID.randomUUID().toString();
        testDocument.description = "testDescription";
        testDocument.name = "testName";
        testDocument.volume = 2;
        return testDocumentRepository.save(testDocument);
    }

}
