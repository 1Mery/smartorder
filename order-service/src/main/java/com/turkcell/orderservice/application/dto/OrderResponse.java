package com.turkcell.orderservice.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        UUID customerId,
        BigDecimal totalPrice,
        String status,
        List<OrderItemResponse> items
) {

    // sipariş içindeki her bir ürün satırı için
    public record OrderItemResponse(
            UUID productId,
            int quantity,
            BigDecimal unitPrice,
            BigDecimal lineTotal
    ) {
    }
}
