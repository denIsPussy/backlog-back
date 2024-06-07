package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.EverythingElse.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Integer> {
}
