package com.onlineshop.onlineshop.Models.DTO.Order;

import com.onlineshop.onlineshop.Models.DTO.Product.ProductNestedDTO;
import com.onlineshop.onlineshop.Models.DTO.Store.StoreNestedDTO;
import com.onlineshop.onlineshop.Models.Database.Order.OrderItem;

public class OrderItemViewDTO {
    private int id;
    private ProductNestedDTO product;
    private int quantity;

    public OrderItemViewDTO(){

    }

    public OrderItemViewDTO(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.product = new ProductNestedDTO(orderItem.getProduct());
        this.quantity = orderItem.getQuantity();
    }

    public int getId() {
        return id;
    }

    public ProductNestedDTO getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
