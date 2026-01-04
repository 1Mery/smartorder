package com.turkcell.orderservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
public class JpaOrderItemEntity {

    @Id
    private UUID orderItemId;   // Domain'deki OrderItemId ile eşleşecek

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private JpaOrderEntity order;

    private UUID productId;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal lineTotal;
}

