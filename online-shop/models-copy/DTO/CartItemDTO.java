package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.CartItem;

public class CartItemDTO {
    private int id;
    private ProductDTO product;
    private ShoppingCartDTO shoppingCart;
    private int quantity;

    public CartItemDTO(){

    }

    public CartItemDTO(CartItem cartItem) {
        this.id = cartItem.getId();
        this.product = new ProductDTO(cartItem.getProduct());
        this.shoppingCart = new ShoppingCartDTO(cartItem.getShoppingCart());
        this.quantity = cartItem.getQuantity();
    }

    public int getId() {
        return id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public ShoppingCartDTO getShoppingCart() {
        return shoppingCart;
    }
}
