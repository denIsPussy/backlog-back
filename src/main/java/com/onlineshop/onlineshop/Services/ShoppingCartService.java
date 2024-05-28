package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.CartItem;
import com.onlineshop.onlineshop.Models.ShoppingCart;
import com.onlineshop.onlineshop.Models.User;
import com.onlineshop.onlineshop.Repositories.CartItemRepository;
import com.onlineshop.onlineshop.Repositories.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public void clear(int id){
        return;
    }

    public ShoppingCart addToCart(ShoppingCart shoppingCart, CartItem cartItem){
        shoppingCart.addToCartItems(cartItem);
        cartItemRepository.save(cartItem);
        return shoppingCartRepository.save(shoppingCart);
    }

    public void removeFromCart(ShoppingCart shoppingCart, int productId){
//        List<Optional<CartItem>> cartItem = cartItemRepository.findByProductId(productId);
//        shoppingCart.removeFromCartItems(productId);
//        cartItemRepository.save(cartItem);
//        return shoppingCartRepository.save(shoppingCart);
    }

    public void update(int productId, int quantity){
        return;
    }

//    public ShoppingCart getByUsername(String username){
//        Optional<ShoppingCart> shoppCart = shoppingCartRepository.findByUsername(username);
//        return shoppCart.orElseThrow();
//    }

    public void reduceProductQuantity(ShoppingCart shoppingCart, int productId){
        shoppingCart.reduceProductQuantity(productId);
        shoppingCartRepository.save(shoppingCart);
    }

    public void increaseProductQuantity(ShoppingCart shoppingCart, int productId){
        shoppingCart.increseProductQuantity(productId);
        shoppingCartRepository.save(shoppingCart);
    }

    public List<ShoppingCart> getByUserId(int userId){
        return null;
    }
}

