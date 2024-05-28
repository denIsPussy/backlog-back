package com.onlineshop.onlineshop.Models.DTO.Store;

import com.onlineshop.onlineshop.Models.DTO.AddressNestedDTO;
import com.onlineshop.onlineshop.Models.Store;

public class StoreNestedDTO {
    private int id;
    private String name;
    private AddressNestedDTO address;

    public StoreNestedDTO(){

    }

    public StoreNestedDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.address = new AddressNestedDTO(store.getAddress());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressNestedDTO getAddress() {
        return address;
    }
}
