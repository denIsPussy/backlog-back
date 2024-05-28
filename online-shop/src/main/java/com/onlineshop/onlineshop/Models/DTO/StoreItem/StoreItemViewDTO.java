package com.onlineshop.onlineshop.Models.DTO.StoreItem;

import com.onlineshop.onlineshop.Models.DTO.Product.ProductNestedDTO;
import com.onlineshop.onlineshop.Models.StoreItem;

public class StoreItemViewDTO {
    private int id;
    private int quantity;
    private ProductNestedDTO productNestedDTO;

    public StoreItemViewDTO(){

    }

    public StoreItemViewDTO(StoreItem storeItem) {
        this.id = storeItem.getId();
        this.quantity = storeItem.getQuantity();
        this.productNestedDTO = new ProductNestedDTO(storeItem.getProduct());
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductNestedDTO getProductNestedDTO() {
        return productNestedDTO;
    }
}
