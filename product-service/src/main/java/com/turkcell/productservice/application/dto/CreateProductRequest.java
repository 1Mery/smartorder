package com.turkcell.productservice.application.dto;

import java.math.BigDecimal;

public record CreateProductRequest(
        String productName,
        BigDecimal amount,
        int stock
) {
}
