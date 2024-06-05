package com.onlineshop.onlineshop.Models.DTO.ShopCart;

import com.onlineshop.onlineshop.Models.EverythingElse.CartItem;
import com.onlineshop.onlineshop.Models.DTO.Product.ProductViewDTO;

public class CartItemViewDTO {
//    private int id;
    private ProductViewDTO product;
    //private ShoppingCartDTO shoppingCart;
    private int quantity;

    public CartItemViewDTO(){

    }

    public CartItemViewDTO(CartItem cartItem) {
//        this.id = cartItem.getId();
        this.product = new ProductViewDTO(cartItem.getProduct());
        //this.shoppingCart = new ShoppingCartDTO(cartItem.getShoppingCart());
        this.quantity = cartItem.getQuantity();
    }

//    public int getId() {
//        return id;
//    }

    public ProductViewDTO getProduct() {
        return product;
    }

    public void setProduct(ProductViewDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


//    public ShoppingCartDTO getShoppingCart() {
//        return shoppingCart;
//    }
}
