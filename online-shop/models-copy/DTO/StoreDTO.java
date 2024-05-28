package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.Address;
import com.onlineshop.onlineshop.Models.Store;
import com.onlineshop.onlineshop.Models.StoreItem;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StoreDTO {
    private int id;
    private String name;
    private AddressDTO address;

    public StoreDTO(){

    }

    public StoreDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.address = new AddressDTO(store.getAddress());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressDTO getAddress() {
        return address;
    }
}
