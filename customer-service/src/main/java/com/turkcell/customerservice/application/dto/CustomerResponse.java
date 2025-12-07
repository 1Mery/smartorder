package com.turkcell.customerservice.application.dto;

import java.util.UUID;

public record CustomerResponse(
        UUID customerId,
        String firstName,
        String lastName,
        String email,
        String phone,
        String city,
        String street
) {
}
