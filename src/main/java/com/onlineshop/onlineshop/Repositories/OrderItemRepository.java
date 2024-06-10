package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.Order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
