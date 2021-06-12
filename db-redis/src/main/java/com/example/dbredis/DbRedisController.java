package com.example.dbredis;

import com.example.dbredis.entity.TestDocument;
import com.example.dbredis.entity.TestDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DbRedisController {
    private TestDocumentRepository testDocumentRepository;

    @Autowired
    public DbRedisController(TestDocumentRepository testDocumentRepository) {
        this.testDocumentRepository = testDocumentRepository;
    }

    @GetMapping("/documents/{id}")
    public TestDocument getDocument(@PathVariable String id) {
        return testDocumentRepository.findById(id).orElse(null);
    }

    @PostMapping("/documents")
    public TestDocument createDocument() {
        TestDocument testDocument = new TestDocument();
        testDocument.id = UUID.randomUUID().toString();
        testDocument.description = "testDescription";
        testDocument.name = "testName";
        testDocument.volume = 2;
        return testDocumentRepository.save(testDocument);
    }
}
