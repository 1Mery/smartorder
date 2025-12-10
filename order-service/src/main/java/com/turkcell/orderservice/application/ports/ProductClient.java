package com.turkcell.orderservice.application.ports;

import java.util.UUID;

public interface ProductClient {
    void getProductInfo(UUID productId);
}
