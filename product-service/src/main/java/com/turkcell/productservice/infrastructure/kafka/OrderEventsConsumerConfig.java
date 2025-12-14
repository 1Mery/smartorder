package com.turkcell.productservice.infrastructure.kafka;

import com.turkcell.productservice.application.dto.DecreaseStockRequest;
import com.turkcell.productservice.application.services.DecreaseStockService;
import com.turkcell.productservice.domain.model.ProductId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class OrderEventsConsumerConfig {

    private final DecreaseStockService service;

    public OrderEventsConsumerConfig(DecreaseStockService service) {
        this.service = service;
    }

    @Bean
    public Consumer<OrderCreatedEvent> orderEvents() {
        return event ->{
            log.info("OrderCreatedEvent received in product-service: " + event);
            ProductId productId = new ProductId(event.productId());
            DecreaseStockRequest request = new DecreaseStockRequest(event.quantity());

            service.decrease(productId,request);
        };

    }
}
