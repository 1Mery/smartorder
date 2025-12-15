package com.turkcell.orderservice.application.events;

import java.util.UUID;

public record StockDecreasedEvent(
        UUID orderId,
        UUID productId,
        int quantity
) {
}
