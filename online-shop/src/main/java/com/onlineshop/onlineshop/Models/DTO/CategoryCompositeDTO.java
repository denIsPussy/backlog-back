package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.Category;

public class CategoryCompositeDTO {
    private int id;
    private String name;
    private String description;

    public CategoryCompositeDTO(){

    }

    public CategoryCompositeDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
