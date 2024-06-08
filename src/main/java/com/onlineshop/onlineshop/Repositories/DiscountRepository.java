package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.EverythingElse.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
}
