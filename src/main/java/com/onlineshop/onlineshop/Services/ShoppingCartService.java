package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.EverythingElse.CartItem;
import com.onlineshop.onlineshop.Models.EverythingElse.ShoppingCart;
import com.onlineshop.onlineshop.Repositories.CartItemRepository;
import com.onlineshop.onlineshop.Repositories.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public void clear(int id){
        return;
    }

    public ShoppingCart addToCart(CartItem cartItem){
        ShoppingCart cart = shoppingCartRepository.findById(cartItem.getCart().getId()).orElseThrow();
        cart.addToCartItems(cartItem);
        logger.info("Quantity of the product in the cart item: {}", cartItem.getQuantity());
        cartItemRepository.save(cartItem);
        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart removeFromCart(int cartId, int productId){
        Optional<ShoppingCart> optCart = shoppingCartRepository.findById(cartId);
        ShoppingCart cart = optCart.orElseThrow();
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = cartItems.stream().filter(item -> item.getProduct().getId() == productId).findFirst().orElseThrow(() -> new RuntimeException("Product not found in cart"));;
        cart.removeFromCartItems(cartItem);
        cartItemRepository.delete(cartItem);
        return shoppingCartRepository.save(cart);
    }
//    public ShoppingCart removeFromCart(int cartId, int productId){
//        ShoppingCart cart = shoppingCartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
//        List<CartItem> items = cart.getCartItems();
//        CartItem itemToRemove = items.stream()
//                .filter(item -> item.getProduct().getId() == productId)
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Product not found in cart"));
//
//        items.remove(itemToRemove); // Удаляем товар из списка
//        //cartItemRepository.delete(itemToRemove); // Удаляем товар из базы данных
//        return shoppingCartRepository.save(cart); // Сохраняем измененную корзину
//    }

    public void update(int productId, int quantity){
        return;
    }

//    public ShoppingCart getByUsername(String username){
//        Optional<ShoppingCart> shoppCart = shoppingCartRepository.findByUsername(username);
//        return shoppCart.orElseThrow();
//    }

    public ShoppingCart reduceProductQuantity(ShoppingCart shoppingCart, int productId){
        ShoppingCart cart = shoppingCartRepository.findById(shoppingCart.getId()).orElseThrow();
        logger.info("cart id {}", cart.getId());
        logger.info("product id {}", productId);
        int quantity = cart.reduceProductQuantity(productId);
        if (quantity == 0){
            return removeFromCart(shoppingCart.getId(), productId);
        }
        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart increaseProductQuantity(ShoppingCart shoppingCart, int productId){
        ShoppingCart cart = shoppingCartRepository.findById(shoppingCart.getId()).orElseThrow();
        cart.increseProductQuantity(productId);
        return shoppingCartRepository.save(cart);
    }

    public List<ShoppingCart> getByUserId(int userId){
        return null;
    }
}

