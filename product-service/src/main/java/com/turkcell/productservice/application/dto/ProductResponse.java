package com.turkcell.productservice.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String productName,
        BigDecimal price,
        int stock,
        boolean active
) {
}
