package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.EverythingElse.PaymentMethod;

public class PaymentMethodCompositeDTO {
    private int id;
    private String description;

    public PaymentMethodCompositeDTO() {

    }

    public PaymentMethodCompositeDTO(PaymentMethod paymentMethod) {
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
