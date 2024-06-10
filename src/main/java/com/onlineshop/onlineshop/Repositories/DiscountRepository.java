package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.Product.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
}
