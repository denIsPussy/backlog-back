package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.Order.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository  extends JpaRepository<Status, Integer> {
}
