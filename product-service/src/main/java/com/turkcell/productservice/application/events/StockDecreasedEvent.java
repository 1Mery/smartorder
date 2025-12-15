package com.turkcell.productservice.application.events;

import java.util.UUID;

public record StockDecreasedEvent(
        UUID orderId,
        UUID productId,
        int quantity
) {
}
