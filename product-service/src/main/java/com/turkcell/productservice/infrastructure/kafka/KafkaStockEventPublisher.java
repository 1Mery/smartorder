package com.turkcell.productservice.infrastructure.kafka;

import com.turkcell.productservice.application.events.StockDecreaseFailedEvent;
import com.turkcell.productservice.application.events.StockDecreasedEvent;
import com.turkcell.productservice.application.events.StockEventPublisher;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class KafkaStockEventPublisher implements StockEventPublisher {

    private final StreamBridge streamBridge;

    public KafkaStockEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publishStockDecreased(StockDecreasedEvent event) {
        streamBridge.send("stockDecreased-out-0", event);
    }

    @Override
    public void publishStockDecreaseFailed(StockDecreaseFailedEvent event) {
        streamBridge.send("stockDecreaseFailed-out-0", event);
    }
}
