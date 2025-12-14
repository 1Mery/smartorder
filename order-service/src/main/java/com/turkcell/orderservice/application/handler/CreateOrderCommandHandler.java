package com.turkcell.orderservice.application.handler;

import com.turkcell.orderservice.application.ports.CustomerClient;
import com.turkcell.orderservice.application.ports.DomainEventPublisher;
import com.turkcell.orderservice.application.ports.ProductClient;
import com.turkcell.orderservice.application.command.CreateOrderCommand;
import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.application.mapper.OrderMapper;
import com.turkcell.orderservice.application.ports.ProductInfo;
import com.turkcell.orderservice.domain.event.OrderCreatedEvent;
import com.turkcell.orderservice.domain.model.CustomerId;
import com.turkcell.orderservice.domain.model.Order;
import com.turkcell.orderservice.domain.model.ProductId;
import com.turkcell.orderservice.domain.ports.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateOrderCommandHandler {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final DomainEventPublisher eventPublisher;


    public CreateOrderCommandHandler(OrderRepository repository, CustomerClient customerClient, ProductClient productClient, OrderMapper mapper, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    public OrderResponse create(CreateOrderCommand command){

        customerClient.verifyCustomer(command.customerId());

        ProductInfo info = productClient.getProductInfo(command.productId());

        int quantity = command.quantity();
        BigDecimal unitPrice = info.price();

        CustomerId customerId = new CustomerId(command.customerId());
        ProductId productId = new ProductId(command.productId());

        Order order = Order.create(customerId, productId, quantity, unitPrice);

        repository.save(order);

        OrderCreatedEvent event= new OrderCreatedEvent(
          order.getOrderId().value(),
          order.getCustomerId().value(),
          order.getProductId().value(),
          order.getQuantity(),
          order.getTotalPrice()
        );

        eventPublisher.publish(event);

        return mapper.toResponse(order);
    }
}
