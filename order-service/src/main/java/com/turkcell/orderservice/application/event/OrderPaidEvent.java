package com.turkcell.orderservice.application.event;

import com.turkcell.orderservice.domain.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderPaidEvent(
        UUID orderId,
        UUID customerId,
        BigDecimal totalPrice,
        OrderStatus status,
        List<OrderItemEvent> items
) {
}

