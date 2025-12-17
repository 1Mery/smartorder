package com.turkcell.orderservice.infrastructure.client;

import com.turkcell.orderservice.application.exception.ProductNotFoundException;
import com.turkcell.orderservice.application.ports.ProductClient;
import com.turkcell.orderservice.application.ports.ProductInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Component
public class ProductWebClientAdapter implements ProductClient {

    private final WebClient productWebClient;

    public ProductWebClientAdapter(WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }

    @Override
    public ProductInfo getProductInfo(UUID productId) {

        try {
            ProductInfoResponse response =
                    productWebClient.get()
                            .uri("/api/v1/products/{id}", productId)
                            .retrieve()  //istek atılıyor ve response alınmaya hazır hale geliyor
                            .bodyToMono(ProductInfoResponse.class)  //gelen JSON productinforesponse çevirir
                            .block(); // sync çalışarak sonucun gelmesini bekliyoruz

            if (response == null) {
                // Çok sıra dışı bir durum
                throw new ProductNotFoundException(
                        "Product not found or empty response for: " + productId
                );
            }

            return new ProductInfo(
                    response.price(),
                    response.stock()
            );

        } catch (WebClientResponseException ex) {

            // Ürün bulunamadı 404
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProductNotFoundException(
                        "Product not found: " + productId
                );
            }

            // Diğer HTTP hataları fırlat
            throw ex;
        }
    }
}
