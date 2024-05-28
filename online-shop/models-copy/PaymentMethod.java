package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.PaymentMethodDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name = "paymentMethods")
public class PaymentMethod {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    @NotNull
    private String description;

    public PaymentMethod(){

    }

    public PaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        this.id = paymentMethodDTO.getId();
        this.description = paymentMethodDTO.getDescription();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
