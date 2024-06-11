package com.onlineshop.onlineshop.Models.DTO.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlineshop.onlineshop.Models.DTO.Store.StoreNestedDTO;
import com.onlineshop.onlineshop.Models.Database.Order.Order;
import com.onlineshop.onlineshop.Models.Database.Order.PaymentMethod;
import com.onlineshop.onlineshop.Models.Database.Order.ShippingMethod;
import com.onlineshop.onlineshop.Models.Database.Order.Status;
import com.onlineshop.onlineshop.Services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderViewDTO {
    private int id;
    private float totalAmount;
    private String creationDate;
    private String completionDate;
    private String deliveryAddress;
    private Status status;
    private PaymentMethod paymentMethod;
    private ShippingMethod shippingMethod;
    private List<OrderItemViewDTO> orderItems;
    private StoreNestedDTO store;
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);


    public OrderViewDTO() {
    }

    public OrderViewDTO(Order order) {
        this.id = order.getId();
        this.totalAmount = order.getTotalAmount();
        this.creationDate = order.getCreationDate().toString();
        this.deliveryAddress = order.getDeliveryAddress();
        this.completionDate = order.getCompletionDate() != null ? order.getCompletionDate().toString() : null;
        this.status = order.getStatus();
        this.paymentMethod = order.getPaymentMethod();
        this.shippingMethod = order.getShippingMethod();
        this.orderItems = order.getOrderItems().stream().map(OrderItemViewDTO::new).toList();
        this.store = new StoreNestedDTO(order.getStore());
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

    public StoreNestedDTO getStore() {
        return store;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}
