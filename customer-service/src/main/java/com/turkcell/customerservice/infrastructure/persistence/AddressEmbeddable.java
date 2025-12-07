package com.turkcell.customerservice.infrastructure.persistence;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class AddressEmbeddable {

    private String city;
    private String street;
}

//Jpa da gömülü olarak tutulan küçük obje