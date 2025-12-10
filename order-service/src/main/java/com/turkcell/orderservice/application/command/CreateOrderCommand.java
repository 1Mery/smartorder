package com.turkcell.orderservice.application.command;

import java.util.UUID;

public record CreateOrderCommand(
        UUID customerId,
        UUID productId,
        int quantity
) {
}
