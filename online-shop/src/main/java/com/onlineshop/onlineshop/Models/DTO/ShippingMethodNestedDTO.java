package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.ShippingMethod;

public class ShippingMethodNestedDTO {
    private int id;
    private String description;

    public ShippingMethodNestedDTO() {

    }

    public ShippingMethodNestedDTO(ShippingMethod shippingMethod) {
        this.id = shippingMethod.getId();
        this.description = shippingMethod.getDescription();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
