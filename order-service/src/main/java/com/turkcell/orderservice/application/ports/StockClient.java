package com.turkcell.orderservice.application.ports;

import java.util.UUID;

public interface StockClient {
    void  checkStock(UUID productId,int quantity);
    void decreaseStock(UUID productId, int quantity);
    void increaseStock(UUID productId, int quantity);
}
