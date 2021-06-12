package com.example.dbredis.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDocumentRepository extends MongoRepository<TestDocument, String> {
}
