package com.turkcell.orderservice.infrastructure.client;

import com.turkcell.orderservice.application.exception.CustomerNotFoundException;
import com.turkcell.orderservice.application.ports.CustomerClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Component
public class CustomerWebClientAdapter implements CustomerClient {

    private final WebClient customerWebClient;

    public CustomerWebClientAdapter(@Qualifier("customerWebClient") WebClient customerWebClient) {
        this.customerWebClient = customerWebClient;
    }

    @Override
    public void verifyCustomer(UUID customerId) {

        try {
            customerWebClient.get()
                    .uri("/api/v1/customers/{id}/verify", customerId)
                    .retrieve()
                    .toBodilessEntity()
                    .block(); // SYNC çağrı

        } catch (WebClientResponseException ex) {

            // Müşteri yoksa 404
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CustomerNotFoundException(
                        "Customer not found: " + customerId
                );
            }

            // Diğer HTTP hatalarını aynen fırlat
            throw ex;
        }
    }
}
