package com.turkcell.orderservice.infrastructure.kafka.outbox;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * saga compensation adımı -> stok arttırmak gerektirdiğinde ters işlem ile productservice'te stoğu arttırmamı sağlar
 */
@Service
public class StockRollbackOutboxService {

    private final OutboxEventRepository outboxEventRepository;

    public StockRollbackOutboxService(OutboxEventRepository outboxEventRepository) {
        this.outboxEventRepository = outboxEventRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void enqueueStockReleaseEvent(UUID orderId, UUID productId, int quantity) {

        String payload = "{\"orderId\":\"" + orderId + "\",\"productId\":\"" + productId + "\",\"quantity\":" + quantity + "}";

        OutboxEventEntity entity = new OutboxEventEntity();
        entity.setEventId(UUID.randomUUID());
        entity.setOrderId(orderId);

        entity.setEventType("STOCK_RELEASE");
        entity.setBindingName("stock-release");
        entity.setPayload(payload);

        entity.setStatus(OutboxStatus.PENDING);
        entity.setCreatedAt(Instant.now());

        outboxEventRepository.save(entity);
    }

}
