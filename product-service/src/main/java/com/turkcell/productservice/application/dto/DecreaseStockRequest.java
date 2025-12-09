package com.turkcell.productservice.application.dto;

import jakarta.validation.constraints.Positive;

public record DecreaseStockRequest(
        @Positive(message = "Stock amount must be greater than zero")
        int stockAmount
) {
}
