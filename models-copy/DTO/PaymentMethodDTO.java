package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.PaymentMethod;

public class PaymentMethodDTO {
    private int id;
    private String description;

    public PaymentMethodDTO() {

    }

    public PaymentMethodDTO(PaymentMethod paymentMethod) {
        this.id = paymentMethod.getId();
        this.description = paymentMethod.getDescription();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
