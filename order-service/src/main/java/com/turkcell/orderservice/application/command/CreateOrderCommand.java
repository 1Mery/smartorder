package com.turkcell.orderservice.application.command;

import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        UUID customerId,
        List<CreateOrderItemCommand> items
) {
    // Her bir ürün için
    public record CreateOrderItemCommand(
            UUID productId,
            int quantity
    ) {
    }
}
