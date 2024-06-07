package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.EverythingElse.ShippingMethod;

public class ShippingMethodCompositeDTO {
    private int id;
    private String description;

    public ShippingMethodCompositeDTO() {

    }

    public ShippingMethodCompositeDTO(ShippingMethod shippingMethod) {
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
