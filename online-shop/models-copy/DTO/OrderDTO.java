package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.*;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private int id;
    private float totalAmount;
    private String creationDate;
    private String completionDate;
    private Status status;
    private PaymentMethod paymentMethod;
    private ShippingMethod shippingMethod;
    private List<CartItemDTO> productList;

    public OrderDTO() {
    }

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.totalAmount = order.getTotalAmount();
        this.creationDate = order.getCreationDate();
        this.completionDate = order.getCompletionDate();
        this.status = order.getStatus();
        this.paymentMethod = order.getPaymentMethod();
        this.shippingMethod = order.getShippingMethod();
        this.productList = order.getProductList().stream().map(CartItemDTO::new).toList();
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

    public List<CartItemDTO> getProductList() {
        return productList;
    }
}
