package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.PaymentMethod;

public class PaymentMethodNestedDTO {
    private int id;
    private String description;

    public PaymentMethodNestedDTO() {

    }

    public PaymentMethodNestedDTO(PaymentMethod paymentMethod) {
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
