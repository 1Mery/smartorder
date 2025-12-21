package com.turkcell.orderservice.application.handler;

import com.turkcell.orderservice.application.event.OrderCreatedEvent;
import com.turkcell.orderservice.application.event.OrderItemEvent;
import com.turkcell.orderservice.application.exception.OrderNotFoundException;
import com.turkcell.orderservice.application.ports.*;
import com.turkcell.orderservice.application.command.CreateOrderCommand;
import com.turkcell.orderservice.application.dto.OrderResponse;
import com.turkcell.orderservice.application.mapper.OrderMapper;
import com.turkcell.orderservice.domain.model.CustomerId;
import com.turkcell.orderservice.domain.model.Order;
import com.turkcell.orderservice.domain.model.OrderItem;
import com.turkcell.orderservice.domain.model.ProductId;
import com.turkcell.orderservice.domain.ports.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CreateOrderCommandHandler {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderEventPublisher eventPublisher;
    private final StockClient stockClient;


    public CreateOrderCommandHandler(OrderRepository repository, CustomerClient customerClient, ProductClient productClient, OrderMapper mapper, OrderEventPublisher eventPublisher, StockClient stockClient) {
        this.repository = repository;
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
        this.stockClient = stockClient;
    }

    public OrderResponse create(CreateOrderCommand command){
        //müşteri kontrolü
        customerClient.verifyCustomer(command.customerId());
        CustomerId customerId = new CustomerId(command.customerId());

        if (command.items() == null || command.items().isEmpty()) {
            throw new OrderNotFoundException("Order must have at least one item");
        }

        //her başarılı decrease stok işlemini not alıyorum (SAGA için)
        record ReservedItem(
                UUID productId,
                int quantity
        ){ }

        List<ReservedItem> reservedItems=new ArrayList<>();

        //sipariş oluşturulup item eklenir
        Order order = null;

        try {
            for (CreateOrderCommand.CreateOrderItemCommand itemCommand : command.items()) {

                ProductId productId = new ProductId(itemCommand.productId());
                int quantity = itemCommand.quantity();

                //stok düşürdüğüm ürünü kayıt altına alıyorum
                stockClient.decreaseStock(itemCommand.productId(),quantity);
                reservedItems.add(new ReservedItem(itemCommand.productId(),quantity));

                //sipariş oluşturma aşamasıyla devam ediyor

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

            List<OrderItemEvent> itemEvents = new ArrayList<>();

            for (OrderItem item : order.getItems()) {
                OrderItemEvent itemEvent = new OrderItemEvent(
                        item.getProductId().value(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getLineTotal()
                );
                itemEvents.add(itemEvent);
            }

            OrderCreatedEvent event = new OrderCreatedEvent(
                    order.getOrderId().value(),
                    order.getCustomerId().value(),
                    order.getTotalPrice(),
                    order.getStatus(),
                    itemEvents
            );

            eventPublisher.publishOrderCreated(event);

            return mapper.toResponse(order);
        }
        catch (Exception exception){
            //siparişteki ürünlerden birinin stok yetersiz olma durumundan dolayı
            //create olmadan stok düşen işlmeleri geri arttırıyoruz
            for (ReservedItem reservedItem:reservedItems){
                try{
                    stockClient.increaseStock(reservedItem.productId(), reservedItem.quantity());
                }
                catch (Exception rollBack){
                    log.error("An error occurred during rollback.productId={}, quantity={}",reservedItem.productId(),reservedItem.quantity(),rollBack);
                }
            }
            throw exception;
        }
    }
}
