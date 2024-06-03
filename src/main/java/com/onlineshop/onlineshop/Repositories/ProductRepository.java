package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Product;
import com.onlineshop.onlineshop.Models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryList_Id(int categoryId);
}
