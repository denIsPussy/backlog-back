package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.Product;

import java.util.List;

public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private float rating;
    private String imageURL;
    private List<DiscountDTO> discountList;
    private List<CategoryDTO> categoryList;
    private List<StoreItemDTO> storeList;
    private List<CartItemDTO> shoppingCartList;

    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.rating = product.getRating();
        this.imageURL = product.getImageURL();
        this.discountList = product.getDiscountList().stream().map(DiscountDTO::new).toList();
        this.storeList = product.getStoreList().stream().map(StoreItemDTO::new).toList();
        this.shoppingCartList = product.getShoppingCartList().stream().map(CartItemDTO::new).toList();
        this.categoryList = product.getCategoryList().stream().map(CategoryDTO::new).toList();
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

    public float getRating() {
        return rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public List<DiscountDTO> getDiscountList() {
        return discountList;
    }

    public List<StoreItemDTO> getStoreList() {
        return storeList;
    }

    public List<CategoryDTO> getCategoryList() {
        return categoryList;
    }

    public List<CartItemDTO> getShoppingCartList() {
        return shoppingCartList;
    }
}
