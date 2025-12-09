package com.turkcell.productservice.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String productName,
        BigDecimal amount,
        int stock,
        boolean active
) {
}
