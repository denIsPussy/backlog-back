package com.onlineshop.onlineshop.Repositories;

import com.onlineshop.onlineshop.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
