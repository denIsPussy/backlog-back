package com.onlineshop.onlineshop.Models.DTO.StoreItem;

import com.onlineshop.onlineshop.Models.DTO.Product.ProductNestedDTO;
import com.onlineshop.onlineshop.Models.DTO.Store.StoreNestedDTO;
import com.onlineshop.onlineshop.Models.StoreItem;

public class StoreItemNestedDTO {
    private int id;
    private int quantity;
    private StoreNestedDTO storeNestedDTO;

    public StoreItemNestedDTO(){

    }

    public StoreItemNestedDTO(StoreItem storeItem) {
        this.id = storeItem.getId();
        this.quantity = storeItem.getQuantity();
        this.storeNestedDTO = new StoreNestedDTO(storeItem.getStore());
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public StoreNestedDTO getStoreNestedDTO() {
        return storeNestedDTO;
    }
}
