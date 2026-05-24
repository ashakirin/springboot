package com.example.orderservice.inventory;

import com.example.orderservice.order.OrderCompleted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

/**
 * Local listener in the {@code inventory} module.
 *
 * <p>{@link ApplicationModuleListener @ApplicationModuleListener} is Spring Modulith's shortcut
 * for {@code @Async} + {@code @TransactionalEventListener} + {@code @Transactional(REQUIRES_NEW)}.
 * Each invocation:
 * <ul>
 *   <li>runs after the publishing transaction commits, so the order row is visible to whatever
 *       this listener does;</li>
 *   <li>runs in its own new transaction, isolating its failures from the publisher;</li>
 *   <li>runs asynchronously, so the {@code POST /orders} response time is not coupled to it.</li>
 * </ul>
 *
 * <p>Spring Modulith writes one {@code event_publication} row for this listener at publication
 * time. If the listener succeeds the row is archived; if it throws, the row is left in
 * {@code FAILED} state for resubmission.
 */
@Component
class InventoryUpdater {

    private static final Logger log = LoggerFactory.getLogger(InventoryUpdater.class);

    @ApplicationModuleListener
    void on(OrderCompleted event) {
        log.info("Inventory: decremented stock for SKU {} by {} (order {})",
                event.productSku(), event.quantity(), event.orderId());
        // Real implementation would update an inventory aggregate here.
    }
}
