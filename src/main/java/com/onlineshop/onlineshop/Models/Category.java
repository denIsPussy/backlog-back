package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.CategoryCompositeDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "description")
    private String description;

    public Category(){

    }

    public Category(CategoryCompositeDTO categoryCompositeDTO) {
        this.id = categoryCompositeDTO.getId();
        this.name = categoryCompositeDTO.getName();
        this.description = categoryCompositeDTO.getDescription();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
