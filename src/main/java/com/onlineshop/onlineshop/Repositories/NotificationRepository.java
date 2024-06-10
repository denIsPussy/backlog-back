package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Database.User.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserId(Integer userId);
}
