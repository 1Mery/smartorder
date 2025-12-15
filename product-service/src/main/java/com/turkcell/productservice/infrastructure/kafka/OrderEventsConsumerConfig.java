package com.turkcell.productservice.infrastructure.kafka;

import com.turkcell.productservice.application.dto.DecreaseStockRequest;
import com.turkcell.productservice.application.events.OrderCreatedEvent;
import com.turkcell.productservice.application.events.StockDecreaseFailedEvent;
import com.turkcell.productservice.application.events.StockDecreasedEvent;
import com.turkcell.productservice.application.events.StockEventPublisher;
import com.turkcell.productservice.application.services.DecreaseStockService;
import com.turkcell.productservice.domain.exception.InsufficientStockException;
import com.turkcell.productservice.domain.model.ProductId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class OrderEventsConsumerConfig {

    private final DecreaseStockService service;
    private final StockEventPublisher stockEventPublisher;

    public OrderEventsConsumerConfig(DecreaseStockService service, StockEventPublisher stockEventPublisher) {
        this.service = service;
        this.stockEventPublisher = stockEventPublisher;
    }

    @Bean
    public Consumer<OrderCreatedEvent> orderEvents() {
        return event ->{
            log.info("OrderCreatedEvent received in product-service: " + event);
            ProductId productId = new ProductId(event.productId());
            DecreaseStockRequest request = new DecreaseStockRequest(event.quantity());

            try {
                // Domain’de stok denemesi
                service.decrease(productId, request);

                // Başarılı StockDecreasedEvent
                StockDecreasedEvent stockDecreasedEvent = new StockDecreasedEvent(
                        event.orderId(),
                        event.productId(),
                        event.quantity()
                );

                log.info("Stock decreased successfully for orderId={}, productId={}, quantity={}",
                        event.orderId(), event.productId(), event.quantity());

                stockEventPublisher.publishStockDecreased(stockDecreasedEvent);

            } catch (InsufficientStockException e) {
                //Yetersiz stok StockDecreaseFailedEvent
                log.warn("Insufficient stock for orderId={}, productId={}, requested={}, available={}",
                        event.orderId(), event.productId(),
                        e.getRequestedQuantity(), e.getAvailableQuantity());

                StockDecreaseFailedEvent failedEvent = new StockDecreaseFailedEvent(
                        event.orderId(),
                        event.productId(),
                        e.getRequestedQuantity(),
                        e.getAvailableQuantity()
                );

                stockEventPublisher.publishStockDecreaseFailed(failedEvent);
            }
        };
    }
}
