package com.turkcell.orderservice.application.handler;

import com.turkcell.orderservice.application.exception.OrderNotFoundException;
import com.turkcell.orderservice.application.ports.*;
import com.turkcell.orderservice.application.command.CreateOrderCommand;
import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.application.mapper.OrderMapper;
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
    private final StockClient stockClient;


    public CreateOrderCommandHandler(OrderRepository repository, CustomerClient customerClient, ProductClient productClient, OrderMapper mapper, DomainEventPublisher eventPublisher, StockClient stockClient) {
        this.repository = repository;
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
        this.stockClient = stockClient;
    }

    public OrderResponse create(CreateOrderCommand command){

        customerClient.verifyCustomer(command.customerId());
        CustomerId customerId = new CustomerId(command.customerId());

        if (command.items() == null || command.items().isEmpty()) {
            throw new OrderNotFoundException("Order must have at least one item");
        }

        //sipariş oluşturulup item eklenir
        Order order = null;

        for (CreateOrderCommand.CreateOrderItemCommand itemCommand : command.items()) {

            ProductId productId = new ProductId(itemCommand.productId());
            int quantity = itemCommand.quantity();

            // Stok kontrolü
            stockClient.checkStock(itemCommand.productId(), quantity);

            // Ürün bilgisi
            ProductInfo info = productClient.getProductInfo(itemCommand.productId());
            BigDecimal unitPrice = info.price();

            if (order == null) {
                order = Order.create(
                        customerId,
                        productId,
                        quantity,
                        unitPrice
                );
            } else {
                order.addItem(
                        productId,
                        quantity,
                        unitPrice
                );
            }
        }

        repository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getOrderId().value(),
                order.getCustomerId().value(),
                order.getTotalPrice()
        );

        eventPublisher.publish(event);

        return mapper.toResponse(order);
    }
}
