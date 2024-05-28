package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.ShippingMethodDTO;
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

    public ShippingMethod(ShippingMethodDTO shippingMethodDTO) {
        this.id = shippingMethodDTO.getId();
        this.description = shippingMethodDTO.getDescription();
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
