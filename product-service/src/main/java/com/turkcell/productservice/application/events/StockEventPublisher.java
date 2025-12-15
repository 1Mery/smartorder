package com.turkcell.productservice.application.events;

public interface StockEventPublisher {

    void publishStockDecreased(StockDecreasedEvent event);

    void publishStockDecreaseFailed(StockDecreaseFailedEvent event);
}
