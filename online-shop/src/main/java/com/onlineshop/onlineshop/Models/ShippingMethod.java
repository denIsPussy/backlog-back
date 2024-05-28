package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.ShippingMethodNestedDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "shippingMethods")
public class ShippingMethod {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    @NotNull
    private String description;

    public ShippingMethod(){

    }

    public ShippingMethod(ShippingMethodNestedDTO shippingMethodNestedDTO) {
        this.id = shippingMethodNestedDTO.getId();
        this.description = shippingMethodNestedDTO.getDescription();
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
