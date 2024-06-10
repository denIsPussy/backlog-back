package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Exceptions.CustomExceptions.ResourceNotFoundException;
import com.onlineshop.onlineshop.Exceptions.CustomExceptions.UnauthorizedAccessException;
import com.onlineshop.onlineshop.Models.Database.User.Notification;
import com.onlineshop.onlineshop.Models.Database.User.User;
import com.onlineshop.onlineshop.Repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public Notification getById(int id){
        return notificationRepository.findById(id).orElseThrow();
    }

    public List<Notification> getByUserId(int userId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден.");
        }

        List<Notification> notifications = notificationRepository.findByUserId(userId);
        if (notifications == null) {
            throw new ResourceNotFoundException("Уведомления для данного пользователя не найдены.");
        }

        notifications = notifications.stream().sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt())).toList();
        if (user.isAreNotificationsEnabled())
            return notifications;
        else
            return notifications.stream().filter(Notification::isSystem).toList();
    }


    public List<Notification> read(int notificationId) {
        Notification notification = getById(notificationId);
        if (notification == null) {
            throw new ResourceNotFoundException("Уведомление не найдено.");
        }
        notification.setRead(true);
        notificationRepository.save(notification);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден.");
        }

        return getByUserId(user.getId());
    }

    public List<Notification> getNewByUserId(int userId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден.");
        }

        List<Notification> notifications = notificationRepository.findByUserId(userId).stream().filter(item -> !item.isRead()).toList();
        if (notifications.isEmpty()) {
            throw new ResourceNotFoundException("Новые уведомления для данного пользователя не найдены.");
        }

        if (user.isAreNotificationsEnabled())
            return notifications;
        else
            return notifications.stream().filter(Notification::isSystem).toList();
    }
}
