package com.turkcell.customerservice.domain.model;

import java.util.Objects;

public record Address(String street,
                      String city) {
    public Address{
        Objects.requireNonNull(street,"Street cannot be null");
        Objects.requireNonNull(city,"City cannot be null");
    }

    public static Address of(String rawStreet, String rawCity){

        if (rawCity==null){
            throw new IllegalArgumentException("City  cannot be null");
        }

        if (rawStreet==null){
            throw new IllegalArgumentException("Street cannot be null");
        }

        rawCity=rawCity.trim();
        rawStreet=rawStreet.trim();

        if (rawCity.isBlank()){
            throw new IllegalArgumentException("Cannot be blank");
        }

        if (rawStreet.isBlank()){
            throw new IllegalArgumentException("Cannot be blank");
        }

        return new Address(rawStreet,rawCity);
    }
}
