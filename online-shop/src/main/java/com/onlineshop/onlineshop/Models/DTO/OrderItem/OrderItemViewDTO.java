package com.onlineshop.onlineshop.Models.DTO.OrderItem;

import com.onlineshop.onlineshop.Models.DTO.Product.ProductNestedDTO;
import com.onlineshop.onlineshop.Models.DTO.Product.ProductViewDTO;
import com.onlineshop.onlineshop.Models.OrderItem;

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
