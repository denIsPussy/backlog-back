package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.ProductDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "price")
    @NotNull
    private float price;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "rating")
    @NotNull
    private float rating;

    @Column(name = "image")
    @NotNull
    private String imageURL;

    @OneToMany(mappedBy = "product")
    private List<StoreItem> storeList;

    @OneToMany(mappedBy = "product")
    private List<CartItem> shoppingCartList;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "product_discount",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "discount_id") }
    )
    private List<Discount> discountList;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "product_category",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private List<Category> categoryList;

    public Product(){

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<StoreItem> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreItem> storeProducts) {
        this.storeList = storeProducts;
    }

    public List<CartItem> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<CartItem> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }
}
