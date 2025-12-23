package com.turkcell.orderservice.infrastructure.kafka.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {
    List<OutboxEventEntity> findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus status);
}
