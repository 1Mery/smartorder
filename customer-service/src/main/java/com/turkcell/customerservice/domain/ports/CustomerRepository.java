package com.turkcell.customerservice.domain.ports;

import com.turkcell.customerservice.domain.model.Customer;
import com.turkcell.customerservice.domain.model.CustomerId;

import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(CustomerId customerId);
    void delete(CustomerId customerId);
}
