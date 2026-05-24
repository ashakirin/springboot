package com.example.orderservice.order;

import com.example.orderservice.order.OrderCompleted;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Order aggregate. The {@code order} package is its own Spring Modulith application module.
 * Other modules ({@code inventory}, future ones) only learn about completed orders via the
 * published {@link OrderCompleted} event — they never touch this entity.
 */
@Entity
@Table(name = "orders")
class Order {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String productSku;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Status status;

    @Column(nullable = false)
    private Instant createdAt;

    protected Order() { /* JPA */ }

    Order(UUID id, String customerEmail, String productSku, int quantity, BigDecimal totalAmount, Instant createdAt) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.productSku = productSku;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = Status.COMPLETED;     // demo: orders are created already completed
        this.createdAt = createdAt;
    }

    UUID getId() { return id; }
    String getCustomerEmail() { return customerEmail; }
    String getProductSku() { return productSku; }
    int getQuantity() { return quantity; }
    BigDecimal getTotalAmount() { return totalAmount; }
    Status getStatus() { return status; }
    Instant getCreatedAt() { return createdAt; }

    enum Status { PENDING, COMPLETED, CANCELLED }
}
