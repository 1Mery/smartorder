package com.turkcell.product_service.application.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateProductRequest(

    @NotBlank String name,
    @NotBlank String description,
    @NotNull@DecimalMin("0.0") BigDecimal amount,
    @Min(0) int quantity,
    @NotBlank @Pattern(regexp = "^[A-Z]+$")String code,
    @NotBlank String symbol,
    @NotBlank String currencyName
) {
}
