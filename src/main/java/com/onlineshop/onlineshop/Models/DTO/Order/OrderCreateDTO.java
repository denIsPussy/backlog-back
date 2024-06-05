package com.onlineshop.onlineshop.Models.DTO.Order;

import com.onlineshop.onlineshop.Models.DTO.OrderItem.OrderItemCreateDTO;
import com.onlineshop.onlineshop.Models.EverythingElse.PaymentMethod;
import com.onlineshop.onlineshop.Models.EverythingElse.ShippingMethod;
import com.onlineshop.onlineshop.Models.EverythingElse.Status;

import java.util.List;

public class OrderCreateDTO {
    private float totalAmount;
    private String creationDate;
    private Status status;
    private PaymentMethod paymentMethod;
    private ShippingMethod shippingMethod;
    private List<OrderItemCreateDTO> orderItems;

    public OrderCreateDTO() {
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public List<OrderItemCreateDTO> getOrderItems() {
        return orderItems;
    }
}
