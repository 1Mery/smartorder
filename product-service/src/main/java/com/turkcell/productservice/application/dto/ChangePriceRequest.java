package com.turkcell.productservice.application.dto;

import java.math.BigDecimal;

public record ChangePriceRequest(
        BigDecimal newAmount
) {
}
