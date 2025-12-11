package com.turkcell.orderservice.infrastructure.persistence;

import com.turkcell.orderservice.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class JpaOrderMapper {

    public Order toDomain(JpaOrderEntity entity){
        return Order.rehydrate(
                new OrderId(entity.getOrderId()),
                new CustomerId(entity.getCustomerId()),
                new ProductId(entity.getProductId()),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getTotalPrice(),
                OrderStatus.valueOf(entity.getStatus())
        );
    }

    public JpaOrderEntity toEntity(Order order){
        JpaOrderEntity orderEntity=new JpaOrderEntity();
        orderEntity.setOrderId(order.getOrderId().value());
        orderEntity.setCustomerId(order.getCustomerId().value());
        orderEntity.setProductId(order.getProductId().value());
        orderEntity.setQuantity(order.getQuantity());
        orderEntity.setUnitPrice(order.getUnitPrice());
        orderEntity.setTotalPrice(order.getTotalPrice());
        orderEntity.setStatus(order.getStatus().toString());

        return orderEntity;
    }
}
