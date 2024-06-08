package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.EverythingElse.Notification;
import com.onlineshop.onlineshop.Repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Notification create(Notification notification){
        return notificationRepository.save(notification);
    }

    public List<Notification> getAll(){
        return null;
    }

    public Notification getById(int id){
        return null;
    }

    public List<Notification> getByUserId(int userId){
        return notificationRepository.findByUserId(userId);
    }
}
