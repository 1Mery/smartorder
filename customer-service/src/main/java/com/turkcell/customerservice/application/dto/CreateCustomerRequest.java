package com.turkcell.customerservice.application.dto;

public record CreateCustomerRequest(
        String firstName,
        String lastName,
        String email,
        String phone,
        String city,
        String street
) {
}
