package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Exceptions.CustomExceptions.ResourceNotFoundException;
import com.onlineshop.onlineshop.Models.Database.ShoppingCart.CartItem;
import com.onlineshop.onlineshop.Models.Database.ShoppingCart.ShoppingCart;
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
        ShoppingCart cart = shoppingCartRepository.findById(cartItem.getCart().getId()).orElseThrow(() -> new ResourceNotFoundException("Ошибка добавления в корзину"));
        cart.addToCartItems(cartItem);
        cartItemRepository.save(cartItem);
        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart removeFromCart(int cartId, int productId){
        Optional<ShoppingCart> optCart = shoppingCartRepository.findById(cartId);
        ShoppingCart cart = optCart.orElseThrow(() -> new ResourceNotFoundException("Ошибка удаления из корзины"));
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = cartItems.stream().filter(item -> item.getProduct().getId() == productId).findFirst().orElseThrow(() -> new ResourceNotFoundException("Товар не найнден в корзине"));
        cart.removeFromCartItems(cartItem);
        cartItemRepository.delete(cartItem);
        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart reduceProductQuantity(ShoppingCart shoppingCart, int productId){
        ShoppingCart cart = shoppingCartRepository.findById(shoppingCart.getId()).orElseThrow(() -> new ResourceNotFoundException("Не удалось изменить количество"));
        int quantity = cart.reduceProductQuantity(productId);
        if (quantity == 0){
            return removeFromCart(shoppingCart.getId(), productId);
        }
        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart increaseProductQuantity(ShoppingCart shoppingCart, int productId){
        ShoppingCart cart = shoppingCartRepository.findById(shoppingCart.getId()).orElseThrow(() -> new ResourceNotFoundException("Не удалось изменить количество"));
        cart.increseProductQuantity(productId);
        return shoppingCartRepository.save(cart);
    }
}

