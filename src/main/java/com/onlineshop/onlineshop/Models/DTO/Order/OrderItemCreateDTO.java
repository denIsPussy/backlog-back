package com.onlineshop.onlineshop.Models.DTO.Order;

public class OrderItemCreateDTO {
    private int productId;
    private int quantity;
    private int storeId;


    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStoreId() {
        return storeId;
    }
}
