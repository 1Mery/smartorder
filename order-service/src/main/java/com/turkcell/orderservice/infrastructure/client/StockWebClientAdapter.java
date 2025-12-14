package com.turkcell.orderservice.infrastructure.client;

import com.turkcell.orderservice.application.exception.InsufficientStockException;
import com.turkcell.orderservice.application.ports.StockClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Component
public class StockWebClientAdapter implements StockClient {

    private final WebClient client;


    public StockWebClientAdapter(
            @Qualifier("productWebClient") WebClient client) {   //springden product service için olan web clientı istiyoruz
        this.client = client;
    }

    @Override
    public void checkStock(UUID productId, int quantity) {
        try {
            client.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v1/products/{id}/check-stock")
                            .queryParam("quantity", quantity) //Stok kontrolü
                            .build(productId))
                    .retrieve()
                    .toBodilessEntity() //cevap almadan çıkma
                    .block();

        } catch (WebClientResponseException e) {

            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new InsufficientStockException(
                        "Insufficient stock for product " + productId
                );
            }
        }
    }
}


//fail-fast business validation