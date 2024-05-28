package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.CartItem;
import com.onlineshop.onlineshop.Models.Product;
import com.onlineshop.onlineshop.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
