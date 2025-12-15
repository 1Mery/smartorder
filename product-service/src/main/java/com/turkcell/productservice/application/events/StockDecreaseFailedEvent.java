package com.turkcell.productservice.application.events;

import java.util.UUID;

public record StockDecreaseFailedEvent (
        UUID orderId,
        UUID productId,
        int requestQuantity,
        int availableQuantity
) {
}
