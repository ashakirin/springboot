package com.example.demomoduletest;

import com.example.demomoduletest.repository.BankAccount;
import com.example.demomoduletest.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class BankAccountRepositoryTestEmbedded {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    private String id1;
    private String id2;

    @BeforeEach
    public void setup() {
        BankAccount bankAccount1 = new BankAccount();
        id1 = UUID.randomUUID().toString();
        bankAccount1.setId(id1);
        bankAccount1.setAccountNumber("testAccount");
        bankAccount1.setAmount(10);
        bankAccountRepository.save(bankAccount1);

        BankAccount bankAccount2 = new BankAccount();
        id2 = UUID.randomUUID().toString();
        bankAccount2.setId(id2);
        bankAccount2.setAccountNumber("testAccount");
        bankAccount2.setAmount(30);
        bankAccountRepository.save(bankAccount2);
    }

    @Test
    public void shouldGetBankAccount() {
        BankAccount bankAccountRead = bankAccountRepository.findById(id1).get();
        assertThat(bankAccountRead.getAccountNumber()).isEqualTo("testAccount");
    }

    @Test
    public void shouldFindAmountGreaterThan20() {
        List<BankAccount> accounts = bankAccountRepository.findByAmountWithQuery(20);
        assertThat(accounts).hasSize(1);
        assertThat(accounts.get(0).getAmount()).isEqualTo(30);
    }
}
