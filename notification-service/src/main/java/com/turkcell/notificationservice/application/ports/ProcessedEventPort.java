package com.turkcell.notificationservice.application.ports;

import com.turkcell.notificationservice.domain.model.EventId;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * amacı: idompotency sağlamak duplicate eventi önlemek
 */
@Component
public interface ProcessedEventPort {
    boolean tryMarkProcessed(EventId eventId, String eventType, UUID aggregateId);
}
