package com.turkcell.orderservice.infrastructure.client;

import com.turkcell.orderservice.application.ports.ProductClient;
import com.turkcell.orderservice.application.ports.ProductInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class ProductWebClientAdapter implements ProductClient {

    private final WebClient productWebClient;

    public ProductWebClientAdapter(WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }

    @Override
    public ProductInfo getProductInfo(UUID productId) {

        ProductInfoResponse response =
                productWebClient.get()
                        .uri("/api/v1/products/{id}", productId)
                        .retrieve()
                        .bodyToMono(ProductInfoResponse.class)
                        .block(); // SYNC

        return new ProductInfo(
                response.price(),
                response.stock()
        );
    }
}
