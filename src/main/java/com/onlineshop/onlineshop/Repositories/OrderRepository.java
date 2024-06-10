package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
