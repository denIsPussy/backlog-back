package com.onlineshop.onlineshop.Models.DTO.Order;

import com.onlineshop.onlineshop.Models.DTO.Store.StoreNestedDTO;

import java.util.List;

public class OrderCreateDTO {
    private float totalAmount;
    //private String creationDate;
    private PaymentMethodCompositeDTO paymentMethod;
    private ShippingMethodCompositeDTO shippingMethod;
    private List<OrderItemCreateDTO> orderItems;
    private StoreNestedDTO store;

    public OrderCreateDTO() {
    }

    public float getTotalAmount() {
        return totalAmount;
    }

//    public String getCreationDate() {
//        return creationDate;
//    }

    public PaymentMethodCompositeDTO getPaymentMethod() {
        return paymentMethod;
    }

    public ShippingMethodCompositeDTO getShippingMethod() {
        return shippingMethod;
    }

    public List<OrderItemCreateDTO> getOrderItems() {
        return orderItems;
    }

    public StoreNestedDTO getStore() {
        return store;
    }
}
