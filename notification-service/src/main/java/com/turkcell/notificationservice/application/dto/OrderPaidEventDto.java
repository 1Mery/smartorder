package com.turkcell.notificationservice.application.dto;

import com.turkcell.notificationservice.domain.model.EventId;

import java.util.List;
import java.util.UUID;

public record OrderPaidEventDto (EventId eventId,
                                 UUID orderId,
                                 UUID customerId,
                                 String email,
                                 List<OrderItemDto> items
) {
}
