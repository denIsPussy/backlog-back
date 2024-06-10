package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.Order.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Integer> {
}
