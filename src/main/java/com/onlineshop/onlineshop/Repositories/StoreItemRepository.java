package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.Store.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreItemRepository extends JpaRepository<StoreItem, Integer> {
}
