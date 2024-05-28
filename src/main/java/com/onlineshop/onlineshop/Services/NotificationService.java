package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.Notification;
import com.onlineshop.onlineshop.Models.Order;
import com.onlineshop.onlineshop.Models.User;
import com.onlineshop.onlineshop.Repositories.NotificationRepository;
import com.onlineshop.onlineshop.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void create(Notification notification){
        return;
    }

    public List<Notification> getAll(){
        return null;
    }

    public Notification getById(int id){
        return null;
    }

    public List<Notification> getByUserId(int userId){
        return null;
    }
}
