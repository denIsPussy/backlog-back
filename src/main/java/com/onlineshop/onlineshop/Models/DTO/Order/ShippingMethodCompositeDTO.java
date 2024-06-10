package com.onlineshop.onlineshop.Models.DTO.Order;

import com.onlineshop.onlineshop.Models.Database.Order.ShippingMethod;

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
