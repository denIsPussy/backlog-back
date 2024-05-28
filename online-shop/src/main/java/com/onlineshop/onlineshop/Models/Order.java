package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.Order.OrderViewDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "totalAmount")
    @NotNull
    private float totalAmount;

    @Column(name = "creationDate")
    @NotNull
    private String creationDate;

    @Column(name = "completionDate")
    private String completionDate;

    @JoinColumn(name = "status_id")
    @ManyToOne
    private Status status;

    @JoinColumn(name = "payment_method_id")
    @ManyToOne
    private PaymentMethod paymentMethod;

    @JoinColumn(name = "shipping_method_id")
    @ManyToOne
    private ShippingMethod shippingMethod;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Order(){

    }

    public Order(OrderViewDTO orderViewDTO) {
        this.id = orderViewDTO.getId();
        this.totalAmount = orderViewDTO.getTotalAmount();
        this.creationDate = orderViewDTO.getCreationDate();
        this.completionDate = orderViewDTO.getCompletionDate();
        this.status = orderViewDTO.getStatus();
        this.paymentMethod = orderViewDTO.getPaymentMethod();
        this.shippingMethod = orderViewDTO.getShippingMethod();
        this.orderItems = orderViewDTO.getOrderItems().stream().map(OrderItem::new).toList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addToOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public void removeFromOrderItems(OrderItem orderItem) {
        this.orderItems.removeIf(item -> item.getId() == orderItem.getId());
    }
}
