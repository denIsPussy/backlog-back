package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.Order.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
}
