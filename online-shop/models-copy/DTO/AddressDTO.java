package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.Address;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class AddressDTO {
    private int id;
    private String city;
    private String street;
    private int houseNumber;
    private int postalCode;
    private int apartment;

    public AddressDTO(){

    }

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.houseNumber = address.getHouseNumber();
        this.postalCode = address.getPostalCode();
        this.apartment = address.getApartment();
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public int getApartment() {
        return apartment;
    }
}
