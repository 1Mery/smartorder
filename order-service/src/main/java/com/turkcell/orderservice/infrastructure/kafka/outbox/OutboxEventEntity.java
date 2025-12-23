package com.turkcell.orderservice.infrastructure.kafka.outbox;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_event")
@Getter
@Setter
@NoArgsConstructor
public class OutboxEventEntity {

    @Id
    private UUID eventId;

    private UUID orderId;

    private String eventType;

    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    private Instant createdAt;

    private Instant sentAt;

    @Column(name = "binding_name", nullable = false)
    private String bindingName;



}
