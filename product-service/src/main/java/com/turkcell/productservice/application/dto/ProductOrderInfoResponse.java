package com.turkcell.productservice.application.dto;

import java.math.BigDecimal;

public record ProductOrderInfoResponse(
        BigDecimal price,
        int stock
) {
}
