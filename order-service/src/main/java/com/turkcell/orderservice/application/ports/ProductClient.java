package com.turkcell.orderservice.application.ports;

import java.util.UUID;

public interface ProductClient {
    ProductInfo getProductInfo(UUID productId);
}
