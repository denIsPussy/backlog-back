package com.onlineshop.onlineshop.Models.DTO.Store;

import com.onlineshop.onlineshop.Models.DTO.AddressNestedDTO;
import com.onlineshop.onlineshop.Models.DTO.StoreItem.StoreItemNestedDTO;
import com.onlineshop.onlineshop.Models.Store;

public class StoreViewDTO {
    private int id;
    private String name;
    private AddressNestedDTO address;
//    private StoreItemNestedDTO storeItemNestedDTO;

    public StoreViewDTO(){

    }

    public StoreViewDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.address = new AddressNestedDTO(store.getAddress());
        //this.storeItemNestedDTO = store.getStoreItems().stream().map(StoreItemNestedDTO::new).toList();
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

//    public StoreItemNestedDTO getStoreItemNestedDTO() {
//        return storeItemNestedDTO;
//    }
}
