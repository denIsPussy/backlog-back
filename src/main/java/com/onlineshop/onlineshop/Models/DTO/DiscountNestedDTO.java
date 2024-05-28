package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.Discount;

public class DiscountNestedDTO {
    private int id;
    private String description;
    private int discountAmountPercentage;

    public DiscountNestedDTO() {

    }

    public DiscountNestedDTO(Discount discount) {
        this.id = discount.getId();
        this.description = discount.getDescription();
        this.discountAmountPercentage = discount.getDiscountAmount();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getDiscountAmountPercentage() {
        return discountAmountPercentage;
    }
}
