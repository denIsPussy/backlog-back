package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.ShopCart.ShoppingCartDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shoppingCarts")
public class ShoppingCart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "cart_id")
    @OneToMany()
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

    public void increseProductQuantity(int productId) {
        this.cartItems.stream().filter(item -> item.getId() == productId).findFirst().orElseThrow().increaseQuantity();
    }

    public void reduceProductQuantity(int productId) {
        this.cartItems.stream().filter(item -> item.getId() == productId).findFirst().orElseThrow().reduceQuantity();
    }
}
