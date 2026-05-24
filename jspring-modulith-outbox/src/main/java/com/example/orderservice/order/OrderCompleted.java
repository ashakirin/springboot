package com.example.orderservice.order;

import org.springframework.modulith.events.Externalized;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Domain event published when an order is completed.
 *
 * <p>The {@link Externalized @Externalized} annotation marks this event for forwarding to a
 * message broker (RabbitMQ in this app). The value uses the {@code target::routingKey} pattern:
 * <ul>
 *   <li>{@code orders.completed} — the broker target. For Spring AMQP this is the exchange name.</li>
 *   <li>{@code #{#this.orderId()}} — SpEL evaluated against the event instance, used as the AMQP
 *       routing key. With a topic exchange consumers can bind on patterns like {@code orders.*}.</li>
 * </ul>
 *
 * <p>This event is part of the {@code order} module's API surface; other modules and external
 * consumers depend on this contract.
 */
@Externalized("orders.completed::#{#this.orderId()}")
public record OrderCompleted(
        UUID orderId,
        String customerEmail,
        String productSku,
        int quantity,
        BigDecimal totalAmount
) {
}
