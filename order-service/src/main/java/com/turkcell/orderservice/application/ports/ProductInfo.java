package com.turkcell.orderservice.application.ports;

import java.math.BigDecimal;

public record ProductInfo(
        BigDecimal price,
        int stock
) {}
