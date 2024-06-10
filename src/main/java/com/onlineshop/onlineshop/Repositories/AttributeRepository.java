package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.Product.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
}

