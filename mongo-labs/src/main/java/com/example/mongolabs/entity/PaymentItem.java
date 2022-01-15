package com.example.mongolabs.entity;

import java.math.BigDecimal;

public class PaymentItem {
    private String id;
    private BigDecimal amount;

    public PaymentItem(String id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
