package com.example.demomoduletest;

import com.example.demomoduletest.repository.BankAccount;
import com.example.demomoduletest.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class BankController {
    private BankAccountRepository bankAccountRepository;

    @Autowired
    public BankController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("/account/{id}")
    public BankAccount getBankAccount(@PathVariable String id) {
        return bankAccountRepository.findById(id).get();
    }

    @PostMapping("/account")
    public void getBankAccount(@RequestBody BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }
}
