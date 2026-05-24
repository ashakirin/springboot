package com.example.orderservice.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

/**
 * REST entry point for placing orders. Returns {@code 201 Created} with a {@code Location}
 * header carrying the new order's id.
 */
@RestController
@RequestMapping("/orders")
class OrderController {

    private final OrderService service;

    OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<PlacedOrderResponse> place(@RequestBody @Valid PlaceOrderRequest request) {
        UUID id = service.place(
                request.customerEmail(),
                request.productSku(),
                request.quantity(),
                request.totalAmount());
        return ResponseEntity.created(URI.create("/orders/" + id)).body(new PlacedOrderResponse(id));
    }

    record PlaceOrderRequest(
            @Email @NotBlank String customerEmail,
            @NotBlank String productSku,
            @Positive int quantity,
            @Positive BigDecimal totalAmount) { }

    record PlacedOrderResponse(UUID id) { }
}
