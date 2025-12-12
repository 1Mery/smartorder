package com.turkcell.customerservice.application.services;

import com.turkcell.customerservice.application.exception.CustomerNotFoundException;
import com.turkcell.customerservice.domain.model.CustomerId;
import com.turkcell.customerservice.domain.ports.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerifyCustomerService {

    private final CustomerRepository repository;

    public VerifyCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void verify(UUID customerId) {
        CustomerId id = new CustomerId(customerId);

        boolean exists = repository.findById(id).isPresent();

        if (!exists) {
            // GlobalExceptionHandler 404 e map ediyor
            throw new CustomerNotFoundException("Customer not found");
        }
        // varsa hiçbir şey yapma
    }
}
