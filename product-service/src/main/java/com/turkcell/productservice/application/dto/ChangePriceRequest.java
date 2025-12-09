package com.turkcell.productservice.application.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ChangePriceRequest(
        @Positive(message = "New price must be positive")
        BigDecimal newAmount
) {
}
