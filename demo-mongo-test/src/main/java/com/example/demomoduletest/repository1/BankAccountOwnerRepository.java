package com.example.demomoduletest.repository1;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankAccountOwnerRepository extends MongoRepository<BankAccountOwner, String> {

}
