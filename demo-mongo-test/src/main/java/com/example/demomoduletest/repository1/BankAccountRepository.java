package com.example.demomoduletest.repository1;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends MongoRepository<BankAccount, String> {

    List<BankAccount> findByAmountGreaterThan(int limit);

    Optional<BankAccount> findByAccountNumber(String accountNumber);

    @Query("{'amount': {$gt : ?0}}")
    List<BankAccount> findByAmountWithQuery(int limit);
}
