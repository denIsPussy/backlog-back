package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.DTO.NotificationDTO;
import com.onlineshop.onlineshop.Models.EverythingElse.Notification;
import com.onlineshop.onlineshop.Models.EverythingElse.User;
import com.onlineshop.onlineshop.Repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        return notificationRepository.findById(id).orElseThrow();
    }

    public List<Notification> getByUserId(int userId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        List<Notification> notifications = notificationRepository.findByUserId(userId).stream().sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt())).toList();
        if (user.isAreNotificationsEnabled())
            return notifications;
        else
            return notifications.stream().filter(Notification::isSystem).toList();
    }

    public List<Notification> read(int notificationId) {
        Notification notification = getById(notificationId);
        notification.setRead(true);
        notificationRepository.save(notification);
        return notificationRepository.findAll();
    }

    public List<Notification> getNewByUserId(int userId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        List<Notification> notifications = notificationRepository.findByUserId(userId).stream().filter(item -> !item.isRead()).toList();
        if (user.isAreNotificationsEnabled())
            return notifications;
        else
            return notifications.stream().filter(Notification::isSystem).toList();
    }
}
