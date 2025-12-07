package com.turkcell.customerservice.application.services;

import com.turkcell.customerservice.application.dto.CustomerResponse;
import com.turkcell.customerservice.application.exception.CustomerNotFoundException;
import com.turkcell.customerservice.application.mapper.CustomerMapper;
import com.turkcell.customerservice.domain.model.Customer;
import com.turkcell.customerservice.domain.model.CustomerId;
import com.turkcell.customerservice.domain.ports.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCustomerById {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public GetCustomerById(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CustomerResponse getById(UUID customerId){
        CustomerId id=new CustomerId(customerId);
        Customer customer=repository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found"));

        return mapper.toResponse(customer);
    }
}
