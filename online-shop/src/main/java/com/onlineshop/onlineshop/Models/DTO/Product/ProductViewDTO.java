package com.onlineshop.onlineshop.Models.DTO.Product;

import com.onlineshop.onlineshop.Models.DTO.CategoryCompositeDTO;
import com.onlineshop.onlineshop.Models.DTO.DiscountNestedDTO;
import com.onlineshop.onlineshop.Models.DTO.StoreItem.StoreItemNestedDTO;
import com.onlineshop.onlineshop.Models.DTO.StoreItem.StoreItemViewDTO;
import com.onlineshop.onlineshop.Models.Product;

import java.util.List;

public class ProductViewDTO {
    private int id;
    private String name;
    private double price;
    private String description;
    private float rating;
    private byte[] image;
    private List<DiscountNestedDTO> discountList;
    private List<CategoryCompositeDTO> categoryList;
    private List<StoreItemNestedDTO> storeList;

    public ProductViewDTO() {
    }

    public ProductViewDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.rating = product.getRating();
        this.image = product.getImage();
        this.discountList = product.getDiscountList().stream().map(DiscountNestedDTO::new).toList();
        this.storeList = product.getStoreList().stream().map(StoreItemNestedDTO::new).toList();
        this.categoryList = product.getCategoryList().stream().map(CategoryCompositeDTO::new).toList();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }

    public byte[] getImage() {
        return image;
    }

    public List<DiscountNestedDTO> getDiscountList() {
        return discountList;
    }

    public List<StoreItemNestedDTO> getStoreList() {
        return storeList;
    }

    public List<CategoryCompositeDTO> getCategoryList() {
        return categoryList;
    }
}
