package com.onlineshop.onlineshop.Models.DTO.OrderItem;

public class OrderItemCreateDTO {
    private int productId;
    private int quantity;

//    public OrderItemDetailDTO(OrderItem orderItem) {
//        this.id = orderItem.getId();
//        this.product = new ProductDetailDTO(orderItem.getProduct());
//        this.quantity = orderItem.getQuantity();
//    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
