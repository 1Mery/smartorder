package com.turkcell.orderservice.infrastructure.client;

import com.turkcell.orderservice.application.ports.CustomerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class CustomerWebClientAdapter implements CustomerClient {

    private final WebClient customerWebClient;

    public CustomerWebClientAdapter(WebClient customerWebClient) {
        this.customerWebClient = customerWebClient;
    }

    @Override
    public void verifyCustomer(UUID customerId) {

        customerWebClient.get()
                .uri("/api/v1/customers/{id}/verify", customerId)
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // SYNC çağrı

    }
}
