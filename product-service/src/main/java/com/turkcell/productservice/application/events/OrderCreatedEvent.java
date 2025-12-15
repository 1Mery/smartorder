package com.turkcell.productservice.application.events;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        UUID customerId,
        UUID productId,
        int quantity,
        BigDecimal totalPrice )
{  }

