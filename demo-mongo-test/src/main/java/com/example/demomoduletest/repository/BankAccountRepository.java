package com.example.demomoduletest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BankAccountRepository extends MongoRepository<BankAccount, String> {

    List<BankAccount> findByAmountGreaterThan(int limit);

    @Query("{'amount': {$gt : ?0}}")
    List<BankAccount> findByAmountWithQuery(int limit);
}
