package com.turkcell.product_service.application.dto.response;

import java.math.BigDecimal;

public record CreateProductResponse(
    String name,
    String description,
    BigDecimal amount,
    int quantity,
    String code,
    String symbol,
    String currencyName
) {
}
