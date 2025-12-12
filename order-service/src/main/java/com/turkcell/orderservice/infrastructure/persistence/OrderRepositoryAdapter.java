package com.turkcell.orderservice.infrastructure.persistence;

import com.turkcell.orderservice.domain.model.Order;
import com.turkcell.orderservice.domain.model.OrderId;
import com.turkcell.orderservice.domain.ports.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepositoryAdapter implements OrderRepository {

    private final JpaOrderMapper mapper;
    private final SpringDataOrderRepository repository;

    public OrderRepositoryAdapter(JpaOrderMapper mapper, SpringDataOrderRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        JpaOrderEntity entity=mapper.toEntity(order);
        entity=repository.save(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return repository.findById(orderId.value()).
                map(mapper::toDomain);
    }

    @Override
    public void delete(OrderId orderId) {
        repository.deleteById(orderId.value());
    }
}
