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
    private DbRedisService dbRedisService;

    @Autowired
    public DbRedisController(DbRedisService dbRedisService) {
        this.dbRedisService = dbRedisService;
    }

    @GetMapping("/documents/{id}")
    public TestDocument getDocument(@PathVariable String id) {
        return dbRedisService.getDocument(id);
    }

    @PostMapping("/documents")
    public TestDocument createDocument() {
        return dbRedisService.createDocument();
    }
}
