package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.OrderDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
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

    @JoinColumn(name = "order_id")
    @OneToMany
    private List<CartItem> productList;

    public Order(){

    }

    public Order(OrderDTO orderDTO) {
        this.id = orderDTO.getId();
        this.totalAmount = orderDTO.getTotalAmount();
        this.creationDate = orderDTO.getCreationDate();
        this.completionDate = orderDTO.getCompletionDate();
        this.status = orderDTO.getStatus();
        this.paymentMethod = orderDTO.getPaymentMethod();
        this.shippingMethod = orderDTO.getShippingMethod();
        this.productList = orderDTO.getProductList().stream().map(CartItem::new).toList();
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

    public List<CartItem> getProductList() {
        return productList;
    }

    public void setProductList(List<CartItem> productList) {
        this.productList = productList;
    }

    public void addToProductList(CartItem cartItem) {
        this.productList.add(cartItem);
    }

    public void removeFromProductList(CartItem cartItem) {
        this.productList.removeIf(item -> item.getId() == cartItem.getId());
    }
}
