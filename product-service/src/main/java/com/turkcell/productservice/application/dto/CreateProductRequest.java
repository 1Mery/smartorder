package com.turkcell.productservice.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Product name cannot be empty")
        String productName,

        @Positive(message = "Price must be positive")
        BigDecimal amount,

        @PositiveOrZero(message = "Stock cannot be negative")
        int stock
) {
}
