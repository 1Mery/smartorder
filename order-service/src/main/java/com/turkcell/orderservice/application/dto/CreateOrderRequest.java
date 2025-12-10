package com.turkcell.orderservice.application.dto;

import java.util.UUID;

public record CreateOrderRequest(
        UUID customerId,
        UUID productId,
        int quantity
) {
}
