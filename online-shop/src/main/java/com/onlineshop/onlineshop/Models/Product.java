package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.Product.ProductNestedDTO;
import com.onlineshop.onlineshop.Models.DTO.Product.ProductViewDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    @OneToMany(mappedBy = "product")
    private List<StoreItem> storeList;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
//
//    @OneToMany(mappedBy = "product")
//    private List<CartItem> cartItems;

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

    public Product(ProductViewDTO productViewDTO){
        this.id = productViewDTO.getId();
        this.name = productViewDTO.getName();
        this.price = productViewDTO.getId();
        this.description = productViewDTO.getDescription();
        this.rating = productViewDTO.getRating();
        this.image = productViewDTO.getImage();
        this.storeList = productViewDTO.getStoreList().stream().map(StoreItem::new).toList();
        this.discountList = productViewDTO.getDiscountList().stream().map(Discount::new).toList();
        this.categoryList = productViewDTO.getCategoryList().stream().map(Category::new).toList();
    }

    public Product(ProductNestedDTO productNestedDTO){
        this.id = productNestedDTO.getId();
        this.name = productNestedDTO.getName();
        this.price = productNestedDTO.getId();
        this.description = productNestedDTO.getDescription();
        this.rating = productNestedDTO.getRating();
        this.image = productNestedDTO.getImage();
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addToOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public void removeFromOrderItems(OrderItem orderItem) {
        this.orderItems.removeIf(item -> item.getId() == orderItem.getId());
    }
//
//    public List<CartItem> getCartItems() {
//        return cartItems;
//    }
//
//    public void setCartItems(List<CartItem> cartItems) {
//        this.cartItems = cartItems;
//    }
//
//    public void addToCartItems(CartItem cartItem) {
//        this.cartItems.add(cartItem);
//    }
//
//    public void removeFromCartItems(CartItem cartItem) {
//        this.cartItems.removeIf(item -> item.getId() == cartItem.getId());
//    }
}
