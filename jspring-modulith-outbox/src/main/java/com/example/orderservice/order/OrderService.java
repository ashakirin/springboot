package com.example.orderservice.order;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.UUID;

/**
 * Application service of the {@code order} module.
 *
 * <p>The {@link #place} method demonstrates the transactional outbox pattern:
 * <ol>
 *   <li>The order is persisted via the {@link OrderRepository}.</li>
 *   <li>An {@link OrderCompleted} event is published via the {@link ApplicationEventPublisher}.</li>
 * </ol>
 *
 * <p>Because Spring Modulith's Event Publication Registry hooks into the publisher, one row per
 * subscribing transactional listener (and one for the externalization listener) is written into
 * the {@code event_publication} table <em>inside the same transaction</em> as the {@code Order}
 * insert. The order row and the outbox rows commit atomically, which is the core guarantee of
 * the outbox pattern.
 */
@Service
public class OrderService {

    private final OrderRepository orders;
    private final ApplicationEventPublisher events;
    private final Clock clock;

    OrderService(OrderRepository orders, ApplicationEventPublisher events, Clock clock) {
        this.orders = orders;
        this.events = events;
        this.clock = clock;
    }

    @Transactional
    public UUID place(String customerEmail, String productSku, int quantity, BigDecimal totalAmount) {
        UUID id = UUID.randomUUID();
        Order order = new Order(id, customerEmail, productSku, quantity, totalAmount, clock.instant());
        orders.save(order);

        events.publishEvent(new OrderCompleted(id, customerEmail, productSku, quantity, totalAmount));
        return id;
    }
}
