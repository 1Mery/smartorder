package com.turkcell.orderservice.application.ports;

import java.util.UUID;

public interface CustomerClient {
    void verifyCustomer(UUID customerId);
}
