package com.turkcell.customerservice.application.services;

import com.turkcell.customerservice.application.dto.CreateCustomerRequest;
import com.turkcell.customerservice.application.dto.CustomerResponse;
import com.turkcell.customerservice.application.mapper.CustomerMapper;
import com.turkcell.customerservice.domain.model.Customer;
import com.turkcell.customerservice.domain.ports.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerService {

    private final CustomerMapper mapper;
    private final CustomerRepository repository;

    public CreateCustomerService(CustomerMapper mapper, CustomerRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public CustomerResponse create(CreateCustomerRequest request){
        Customer customer=mapper.toDomain(request);
        customer=repository.save(customer);

        return mapper.toResponse(customer);
    }
}
