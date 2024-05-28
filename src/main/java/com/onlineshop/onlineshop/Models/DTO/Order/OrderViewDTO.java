package com.onlineshop.onlineshop.Models.DTO.Order;

import com.onlineshop.onlineshop.Models.*;
import com.onlineshop.onlineshop.Models.DTO.OrderItem.OrderItemViewDTO;

import java.util.List;

public class OrderViewDTO {
    private int id;
    private float totalAmount;
    private String creationDate;
    private String completionDate;
    private Status status;
    private PaymentMethod paymentMethod;
    private ShippingMethod shippingMethod;
    private List<OrderItemViewDTO> orderItems;

    public OrderViewDTO() {
    }

    public OrderViewDTO(Order order) {
        this.id = order.getId();
        this.totalAmount = order.getTotalAmount();
        this.creationDate = order.getCreationDate();
        this.completionDate = order.getCompletionDate();
        this.status = order.getStatus();
        this.paymentMethod = order.getPaymentMethod();
        this.shippingMethod = order.getShippingMethod();
        this.orderItems = order.getOrderItems().stream().map(OrderItemViewDTO::new).toList();
    }

    public int getId() {
        return id;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getCompletionDate() {
        return completionDate;
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

    public List<OrderItemViewDTO> getOrderItems() {
        return orderItems;
    }
}
