package com.turkcell.orderservice.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreated(
        UUID orderId,
        UUID customerId,
        UUID productId,
        int quantity,
        BigDecimal totalPrice
) {
}
