package com.turkcell.productservice.application.dto;

import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.UUID;

public record DecreaseStockForOrderRequest(
        UUID requestId,
        UUID orderId,
        List<OrderItems> items

) {
    public record OrderItems(
            UUID productId,
            @Positive int quantity
    ){}
}
