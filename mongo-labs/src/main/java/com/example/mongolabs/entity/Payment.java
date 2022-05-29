package com.example.mongolabs.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Payment {
    @Id
    private String id;
    private String name;
    private BigDecimal amount;
    private Date created;
    private Date updated;
    private List<PaymentItem> paymentItems = new ArrayList<>();

    public Payment(String name, BigDecimal amount) {
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
