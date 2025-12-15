package com.turkcell.orderservice.application.ports;

import java.util.UUID;

public interface StockClient {
    void  checkStock(UUID productId,int quantity);
}
