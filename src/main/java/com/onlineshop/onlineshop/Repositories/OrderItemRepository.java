package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.EverythingElse.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
