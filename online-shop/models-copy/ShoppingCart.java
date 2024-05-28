package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.ShoppingCartDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shoppingCarts")
public class ShoppingCart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "shoppingCart")
    private List<CartItem> cartItems;

    public ShoppingCart() {
    }

    public ShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        this.id = shoppingCartDTO.hashCode();
        this.cartItems = shoppingCartDTO.getCartItems().stream().map(CartItem::new).toList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void addToCartItems(CartItem item) {
        this.cartItems.add(item);
    }

    public void removeFromCartItems(CartItem cartItem) {
        this.cartItems.removeIf(item -> item.getId() == cartItem.getId());
    }
}
