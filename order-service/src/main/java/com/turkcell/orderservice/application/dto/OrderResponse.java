package com.turkcell.orderservice.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(
         UUID orderId,
         UUID customerId,
         UUID productId,
         int quantity,
         BigDecimal unitPrice,
         BigDecimal totalPrice,
         String status
) {
}
