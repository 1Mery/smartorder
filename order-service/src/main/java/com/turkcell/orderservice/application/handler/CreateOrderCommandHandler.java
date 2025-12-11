package com.turkcell.orderservice.application.handler;

import com.turkcell.orderservice.application.ports.CustomerClient;
import com.turkcell.orderservice.application.ports.ProductClient;
import com.turkcell.orderservice.application.command.CreateOrderCommand;
import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.application.mapper.OrderMapper;
import com.turkcell.orderservice.application.ports.ProductInfo;
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

    public CreateOrderCommandHandler(OrderRepository repository, CustomerClient customerClient, ProductClient productClient, OrderMapper mapper) {
        this.repository = repository;
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.mapper = mapper;
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

        return mapper.toResponse(order);
    }
}
