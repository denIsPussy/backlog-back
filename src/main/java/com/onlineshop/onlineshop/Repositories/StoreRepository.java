package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.Store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
}
