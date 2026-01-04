package com.turkcell.orderservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class JpaOrderEntity {

    @Id
    private UUID orderId;

    private UUID customerId;

    private BigDecimal totalPrice;
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<JpaOrderItemEntity> items;
}
