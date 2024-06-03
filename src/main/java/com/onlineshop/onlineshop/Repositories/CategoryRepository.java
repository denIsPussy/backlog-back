package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.CartItem;
import com.onlineshop.onlineshop.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
