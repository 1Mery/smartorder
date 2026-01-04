package com.turkcell.orderservice.infrastructure.persistence;

import com.turkcell.orderservice.domain.model.*;
import com.turkcell.orderservice.infrastructure.persistence.entity.JpaOrderEntity;
import com.turkcell.orderservice.infrastructure.persistence.entity.JpaOrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JpaOrderMapper {

    public Order toDomain(JpaOrderEntity entity) {
        OrderId orderId = new OrderId(entity.getOrderId());
        CustomerId customerId = new CustomerId(entity.getCustomerId());
        OrderStatus status = OrderStatus.valueOf(entity.getStatus());

        List<OrderItem> items = new ArrayList<>();

        for (JpaOrderItemEntity itemEntity : entity.getItems()) {

            OrderItem item = toDomainItem(itemEntity);

            items.add(item);
        }

        return Order.rehydrate(
                orderId,
                customerId,
                items,
                status
        );
    }

    private OrderItem toDomainItem(JpaOrderItemEntity itemEntity) {
        OrderItemId itemId = new OrderItemId(itemEntity.getOrderItemId());
        ProductId productId = new ProductId(itemEntity.getProductId());

        return OrderItem.rehydrate(
                itemId,
                productId,
                itemEntity.getQuantity(),
                itemEntity.getUnitPrice(),
                itemEntity.getLineTotal()
        );
    }

    public JpaOrderEntity toEntity(Order order) {
        JpaOrderEntity orderEntity = new JpaOrderEntity();
        orderEntity.setOrderId(order.getOrderId().value());
        orderEntity.setCustomerId(order.getCustomerId().value());
        orderEntity.setTotalPrice(order.getTotalPrice());
        orderEntity.setStatus(order.getStatus().toString());

        List<JpaOrderItemEntity> itemEntities = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            JpaOrderItemEntity itemEntity = new JpaOrderItemEntity();
            itemEntity.setOrderItemId(item.getOrderItemId().value());
            itemEntity.setOrder(orderEntity);
            itemEntity.setProductId(item.getProductId().value());
            itemEntity.setQuantity(item.getQuantity());
            itemEntity.setUnitPrice(item.getUnitPrice());
            itemEntity.setLineTotal(item.getLineTotal());

            itemEntities.add(itemEntity);
        }

        orderEntity.setItems(itemEntities);

        return orderEntity;
    }
}
