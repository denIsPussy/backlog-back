package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.CartItemDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cartItems")
public class CartItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
    @NotNull
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    public CartItem(){
    }

    public CartItem(CartItemDTO cartItemDTO){
        this.id = cartItemDTO.getId();
        this.product = new Product(cartItemDTO.getProduct());
        this.shoppingCart = new ShoppingCart(cartItemDTO.getShoppingCart());
        this.quantity = cartItemDTO.getQuantity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
