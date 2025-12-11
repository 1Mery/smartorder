package com.turkcell.customerservice.infrastructure.persistence;

import com.turkcell.customerservice.domain.model.Customer;
import com.turkcell.customerservice.domain.model.CustomerId;
import com.turkcell.customerservice.domain.ports.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerEntityMapper mapper;
    private final SpringDataCustomerRepository repository;

    public CustomerRepositoryAdapter(CustomerEntityMapper mapper, SpringDataCustomerRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Customer save(Customer customer) {
        JpaCustomerEntity entity=mapper.toEntity(customer);
        entity=repository.save(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Customer> findById(CustomerId customerId) {
        return repository
                .findById(customerId.value())
                .map(mapper::toDomain);
    }

    @Override
    public void delete(CustomerId customerId) {
        repository.deleteById(customerId.value());
    }
}
