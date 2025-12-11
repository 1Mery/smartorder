package com.turkcell.orderservice.infrastructure.client;

import java.math.BigDecimal;

public record ProductInfoResponse(
        BigDecimal price,
        int stock
) {}
//Bu sınıf WebClient’in product-service’ten JSON olarak alacağı veriyi temsil eder
