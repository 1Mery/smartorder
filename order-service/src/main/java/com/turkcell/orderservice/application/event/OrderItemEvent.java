package com.turkcell.orderservice.application.event;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemEvent(
        UUID productId,
        int quantity ,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}
