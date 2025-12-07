package com.turkcell.customerservice.application.services;

import com.turkcell.customerservice.domain.model.CustomerId;
import com.turkcell.customerservice.domain.ports.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCustomerService {

    private final CustomerRepository repository;

    public DeleteCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void delete(UUID customerId){
        CustomerId id=new CustomerId(customerId);
        repository.delete(id);
    }
}
