package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.ShoppingCart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
