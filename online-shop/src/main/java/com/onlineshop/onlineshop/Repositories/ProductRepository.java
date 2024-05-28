package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Product;
import com.onlineshop.onlineshop.Models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
