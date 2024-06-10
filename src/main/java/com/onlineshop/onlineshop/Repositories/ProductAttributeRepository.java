package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.Product.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
}
