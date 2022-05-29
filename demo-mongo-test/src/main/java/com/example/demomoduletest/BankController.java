package com.example.demomoduletest;

import com.example.demomoduletest.repository1.BankAccount;
import com.example.demomoduletest.repository1.BankAccountOwner;
import com.example.demomoduletest.repository1.BankAccountOwnerRepository;
import com.example.demomoduletest.repository1.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BankController {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountOwnerRepository bankAccountOwnerRepository;

    @Autowired
    public BankController(BankAccountRepository bankAccountRepository, BankAccountOwnerRepository bankAccountOwnerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountOwnerRepository = bankAccountOwnerRepository;
    }

    @GetMapping("/account/{id}")
    public BankAccount getBankAccount(@PathVariable String id) {
        return bankAccountRepository.findById(id).get();
    }

    @PostMapping("/account")
    public void postBankAccount(@RequestBody BankAccount bankAccount) {
        BankAccountOwner owner = new BankAccountOwner();
        owner.setId("2");
        owner.setName("TestOwner2");
        bankAccountOwnerRepository.save(owner);
        bankAccount.setAccountOwner(owner);
        bankAccountRepository.save(bankAccount);
    }
}
