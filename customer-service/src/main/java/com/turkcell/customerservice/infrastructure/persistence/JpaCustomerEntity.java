package com.turkcell.customerservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class JpaCustomerEntity {

    @Id
    private UUID customerId;

    private String firstName;
    private String lastName;

    private String email;

    private String phone;

    @Embedded
    private AddressEmbeddable address;

}
