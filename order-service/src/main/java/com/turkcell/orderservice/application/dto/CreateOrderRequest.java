package com.turkcell.orderservice.application.dto;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        UUID customerId,
        List<CreateOrderItemRequest> items
) {
    // her satır ürün için dto
    public record CreateOrderItemRequest(
            UUID productId,
            int quantity
    ) {
    }
}
