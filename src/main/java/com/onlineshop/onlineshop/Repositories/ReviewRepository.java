package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.Product.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProductId(int productId);
}
