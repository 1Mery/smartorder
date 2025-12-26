package com.turkcell.productservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class JpaProductEntity {
    @Id
    @Column(name = "id")
    private UUID productId;

    private String productName;

    private BigDecimal amount;

    private int stock;

    @Version  //optimisticlock kullanarak 2 istek aynı geldiğinde racecondition olmasın
    private Long version;

    private boolean active;
}
