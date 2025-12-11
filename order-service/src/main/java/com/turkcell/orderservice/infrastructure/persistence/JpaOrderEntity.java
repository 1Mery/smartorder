package com.turkcell.orderservice.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private UUID productId;

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String status;
}
