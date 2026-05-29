package com.example.orderservice.inventory;

import com.example.orderservice.order.OrderCompleted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Inventory module's RabbitMQ consumer.
 *
 * <p>Receives {@link OrderCompleted} events <em>from RabbitMQ</em>, not from the in-process
 * Spring application event bus. This listener is on the consumer side of the broker boundary:
 *
 * <ol>
 *   <li>{@code OrderService} publishes the application event in a transaction.</li>
 *   <li>Spring Modulith's externalization listener picks it up after commit and sends an AMQP
 *       message to exchange {@code orders.completed}. That listener owns the only outbox row
 *       for this event in the order-service.</li>
 *   <li>RabbitMQ routes the message to the queue declared below; this listener consumes it.</li>
 * </ol>
 *
 * <p>From the order-service's outbox perspective, anything that happens here is invisible: the
 * outbox row is already {@code COMPLETED} once RabbitMQ acks the publish. Failures here are
 * handled by RabbitMQ's redelivery / dead-letter mechanisms, not by Spring Modulith.
 *
 * <p>In a real architecture this listener would live in a separate {@code inventory-service}
 * application with its own database. It lives in this JVM purely so the demo runs as a single
 * Compose stack.
 */
@Component
class InventoryListener {

    static final String QUEUE = "inventory.orders-completed";
    static final String EXCHANGE = "orders.completed";

    private static final Logger log = LoggerFactory.getLogger(InventoryListener.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = QUEUE, durable = "true"),
            exchange = @Exchange(name = EXCHANGE, type = "topic", durable = "true"),
            key = "#"))
    void on(OrderCompleted event) {
        log.info("Inventory (via RabbitMQ): decremented stock for SKU {} by {} (order {})",
                event.productSku(), event.quantity(), event.orderId());
        // Real implementation would update an inventory aggregate here.
    }
}
