package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.CartItem;
import com.onlineshop.onlineshop.Models.ShoppingCart;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO {
    private int id;
    private List<CartItemDTO> cartItems;

    public ShoppingCartDTO(){

    }

    public ShoppingCartDTO(ShoppingCart shoppingCart) {
        this.id = shoppingCart.getId();
        this.cartItems = shoppingCart.getCartItems().stream().map(CartItemDTO::new).toList();
    }

    public int getId() {
        return id;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }
}
