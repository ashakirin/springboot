package com.example.demomoduletest.repository1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeAlias("BankAccount")
public class BankAccount {
    @Id
    private String id;

    private String accountNumber;
    private String sepa;
    private int amount;

    @DBRef
    private BankAccountOwner accountOwner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSepa() {
        return sepa;
    }

    public void setSepa(String sepa) {
        this.sepa = sepa;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BankAccountOwner getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(BankAccountOwner accountOwner) {
        this.accountOwner = accountOwner;
    }
}
