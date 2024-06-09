package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.EverythingElse.Notification;
import com.onlineshop.onlineshop.Models.EverythingElse.User;
import com.onlineshop.onlineshop.Repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        if (user.isAreNotificationsEnabled())
            return notifications;
        else
            return notifications.stream().filter(Notification::isSystem).toList();
    }
}
