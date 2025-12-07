package com.turkcell.customerservice.infrastructure.persistence;

import com.turkcell.customerservice.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityMapper {

    public Customer toDomain(JpaCustomerEntity entity){
        return Customer.rehydrate(
                new CustomerId(entity.getCustomerId()),
                entity.getFirstName(),
                entity.getLastName(),
                new Email(entity.getEmail()),
                new Phone(entity.getPhone()),
                new Address(entity.getAddress().getCity(),entity.getAddress().getStreet())
        );
    }

    public JpaCustomerEntity toEntity(Customer customer){
        JpaCustomerEntity customerEntity=new JpaCustomerEntity();
        AddressEmbeddable addressEmb = new AddressEmbeddable();
        customerEntity.setCustomerId(customer.getCustomerId().value());
        customerEntity.setFirstName(customer.getFirstName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setEmail(customer.getEmail().value());
        customerEntity.setPhone(customer.getPhone().value());
        addressEmb.setCity(customer.getAddress().city());
        addressEmb.setStreet(customer.getAddress().street());
        customerEntity.setAddress(addressEmb);

        return customerEntity;
    }
}
