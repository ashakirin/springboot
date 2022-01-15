package com.example.mongolabs.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Document
public class Payment {
    @Id
    final private String id;
    final private String name;
    final private BigDecimal amount;
    private List<PaymentItem> paymentItems = new ArrayList<>();

    public Payment(String id, String name, BigDecimal amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<PaymentItem> getPaymentItems() {
        return paymentItems;
    }
}
