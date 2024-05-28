package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.DiscountDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "discounts")
public class Discount {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "discountAmountPercentage")
    @NotNull
    private int discountAmountPercentage;

    public Discount(){

    }

    public Discount(DiscountDTO discount) {
        this.id = discount.getId();
        this.description = discount.getDescription();
        this.discountAmountPercentage = discount.getDiscountAmountPercentage();
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

    public int getDiscountAmount() {
        return discountAmountPercentage;
    }

    public void setDiscountAmount(int discountAmountPercentage) {
        this.discountAmountPercentage = discountAmountPercentage;
    }
}
