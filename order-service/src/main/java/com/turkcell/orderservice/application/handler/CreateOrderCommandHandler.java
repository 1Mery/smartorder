package com.turkcell.orderservice.application.handler;

import com.turkcell.orderservice.application.event.OrderCreatedEvent;
import com.turkcell.orderservice.application.event.OrderItemEvent;
import com.turkcell.orderservice.application.exception.InsufficientStockException;
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
import com.turkcell.orderservice.infrastructure.kafka.outbox.OutboxEventEntity;
import com.turkcell.orderservice.infrastructure.kafka.outbox.OutboxEventRepository;
import com.turkcell.orderservice.infrastructure.kafka.outbox.OutboxPayloadSerializer;
import com.turkcell.orderservice.infrastructure.kafka.outbox.OutboxStatus;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
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
    private final OutboxEventRepository outboxEventRepository;
    private final StockClient stockClient;
    private final OutboxPayloadSerializer serializer;

    public CreateOrderCommandHandler(OrderRepository repository, CustomerClient customerClient, ProductClient productClient, OrderMapper mapper, OutboxEventRepository outboxEventRepository, StockClient stockClient, OutboxPayloadSerializer serializer) {
        this.repository = repository;
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.mapper = mapper;
        this.outboxEventRepository = outboxEventRepository;
        this.stockClient = stockClient;
        this.serializer = serializer;
    }

    @Transactional
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

        //tüm ürünleri kontrol ediyoruz
        List<String> insufficient = new ArrayList<>();

        try {

            for (CreateOrderCommand.CreateOrderItemCommand item : command.items()) {
                ProductInfo info = productClient.getProductInfo(item.productId());
                BigDecimal unitPrice = info.price();

                ProductId productId = new ProductId(item.productId());
                int quantity = item.quantity();

                if (order == null) {
                    order = Order.create(customerId, productId, quantity, unitPrice);
                } else {
                    order.addItem(productId, quantity, unitPrice);
                }
            }

            //Stok düşürmeyi dene yetersiz olanları topla
            for (CreateOrderCommand.CreateOrderItemCommand item : command.items()) {
                try {
                    stockClient.decreaseStock(item.productId(), item.quantity());
                    reservedItems.add(new ReservedItem(item.productId(), item.quantity()));
                } catch (InsufficientStockException ex) {
                    insufficient.add("productId=" + item.productId() + ", requested=" + item.quantity());
                }
            }

            // eğer en az 1 ürün yetersizse
            if (!insufficient.isEmpty()) {
                throw new InsufficientStockException(
                        "Insufficient stock for: " + String.join(" | ", insufficient)
                );
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

            String payload =serializer.toJson (event);//event'i json stringe çeviriyorum objectmapperla

            OutboxEventEntity eventEntity=new OutboxEventEntity();
            eventEntity.setEventId(UUID.randomUUID());
            eventEntity.setOrderId(order.getOrderId().value());
            eventEntity.setEventType("OrderCreatedEvent");
            eventEntity.setPayload(payload);
            eventEntity.setStatus(OutboxStatus.PENDING);
            eventEntity.setCreatedAt(Instant.now());
            eventEntity.setBindingName("orderCreateEvents-out-0");

            outboxEventRepository.save(eventEntity);

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
            if (order != null) {
                order.cancel();   //oluşamayan siparişi iptal ediyoruz
                repository.save(order);
            }

            throw exception;
        }
    }
}
