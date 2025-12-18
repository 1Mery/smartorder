package com.turkcell.orderservice.application.event;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderPaidEvent(
        UUID orderId,
        UUID customerId,
        BigDecimal totalPrice,
        String status,
        List<OrderItemEvent> items
) {
}

