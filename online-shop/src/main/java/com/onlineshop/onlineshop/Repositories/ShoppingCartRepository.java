package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.CartItem;
import com.onlineshop.onlineshop.Models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

}
