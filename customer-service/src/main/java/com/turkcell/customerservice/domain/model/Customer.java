package com.turkcell.customerservice.domain.model;

import java.util.Objects;

public class Customer {

    private final CustomerId customerId;

    //primitive
    private String firstName;
    private String lastName;

    private Address address;

    private Phone phone;

    private Email email;

    private Customer(CustomerId customerId, String firstName, String lastName, Address address, Phone phone, Email email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public static Customer create(
            String firstName,
            String lastName,
            Email email,
            Phone phone,
            Address address
    ) {
        validateFirstName(firstName);
        validateLastName(lastName);
        Objects.requireNonNull(email, "Email cannot be null");
        Objects.requireNonNull(phone, "Phone cannot be null");
        Objects.requireNonNull(address, "Address cannot be null");

        return new Customer(CustomerId.generate(), firstName, lastName, address, phone, email);
    }

    public static Customer rehydrate(CustomerId customerId, String firstName, String lastName, Email email, Phone phone, Address address) {
        return new Customer(customerId, firstName, lastName, address, phone, email);
    }

    public static void validateFirstName(String firstName) {
        if (firstName == null)
            throw new IllegalArgumentException("Cannot be null");

        if (firstName.isBlank())
            throw new IllegalArgumentException("Cannot be blank");

        for (int i = 0; i < firstName.length(); i++) {
            char ch = firstName.charAt(i);
            if (Character.isDigit(ch)) {
                throw new IllegalArgumentException("Firstname cannot contain digit");
            }
        }
    }

    public static void validateLastName(String lastName) {
        if (lastName == null)
            throw new IllegalArgumentException("Cannot be null");

        if (lastName.isBlank())
            throw new IllegalArgumentException("Cannot be blank");

        for (int i = 0; i < lastName.length(); i++) {
            char ch = lastName.charAt(i);
            if (Character.isDigit(ch)) {
                throw new IllegalArgumentException("LastName cannot contain digit");
            }
        }
    }

    public void changeFirstName(String newName) {
        validateFirstName(newName);
        this.firstName = newName;
    }

    public void changeLastName(String newName) {
        validateLastName(newName);
        this.lastName = newName;
    }

    public void changePhone(Phone newphone) {
        Objects.requireNonNull(newphone);
        this.phone = newphone;
    }

    public void changeAddress(Address newAddress) {
        Objects.requireNonNull(newAddress);
        this.address = newAddress;
    }

    public void changeEmail(Email newEmail) {
        Objects.requireNonNull(newEmail);
        this.email = newEmail;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }
}
