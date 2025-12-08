package com.turkcell.customerservice.application.mapper;

import com.turkcell.customerservice.application.dto.CreateCustomerRequest;
import com.turkcell.customerservice.application.dto.CustomerResponse;
import com.turkcell.customerservice.domain.model.Address;
import com.turkcell.customerservice.domain.model.Customer;
import com.turkcell.customerservice.domain.model.Email;
import com.turkcell.customerservice.domain.model.Phone;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer){
        return new CustomerResponse(
                customer.getCustomerId().value(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail().value(),
                customer.getPhone().value(),
                customer.getAddress().city(),
                customer.getAddress().street()
        );
    }

    public Customer toDomain(CreateCustomerRequest request){
        Email email =  Email.of(request.email());
        Phone phone =  Phone.of(request.phone());
        Address address = Address.of(request.city(), request.street());

        Customer customer=Customer.create(
                request.firstName(),
                request.lastName(),
                email,
                phone,
                address
        );
        return customer;
    }
}
