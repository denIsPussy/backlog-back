package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Exceptions.CustomExceptions.OutOfStockException;
import com.onlineshop.onlineshop.Models.Database.Order.Order;
import com.onlineshop.onlineshop.Models.Database.ShoppingCart.ShoppingCart;
import com.onlineshop.onlineshop.Models.Database.Store.Store;
import com.onlineshop.onlineshop.Models.Database.Store.StoreItem;
import com.onlineshop.onlineshop.Models.Database.User.User;
import com.onlineshop.onlineshop.Repositories.OrderRepository;
import com.onlineshop.onlineshop.Repositories.StoreRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public List<Store> getByCart() throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        List<Store> storeList = storeRepository.findAll();
        ShoppingCart cart = user.getShoppingCart();
        List<Set<Store>> storeSets = cart.getCartItems().stream()
                .map(item -> item.getProduct().getStoreList().stream()
                        .map(StoreItem::getStore)
                        .collect(Collectors.toSet()))
                .toList();
        if (storeSets.isEmpty()) {
            throw new OutOfStockException("Все товары в корзине отсутствуют в наличии.");
        }

        Set<Store> commonStores = new HashSet<>(storeSets.get(0));
        storeSets.forEach(commonStores::retainAll);

        if (commonStores.isEmpty()){
            throw new OutOfStockException("Не найден магазин, способный предоставить все выбранные товары.");
        }

        return new ArrayList<>(commonStores);
    }
}