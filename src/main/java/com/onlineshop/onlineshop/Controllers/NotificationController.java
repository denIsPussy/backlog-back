package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Models.DTO.Notification.NotificationDTO;
import com.onlineshop.onlineshop.Models.Database.User.Notification;
import com.onlineshop.onlineshop.Models.Database.User.User;
import com.onlineshop.onlineshop.Services.NotificationService;
import com.onlineshop.onlineshop.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public List<NotificationDTO> getByUser() throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        return notificationService.getByUserId(user.getId()).stream().map(NotificationDTO::new).toList();
    }

    @GetMapping("/new")
    public List<NotificationDTO> getNewByUser() throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        return notificationService.getNewByUserId(user.getId()).stream().map(NotificationDTO::new).toList();
    }

    @PostMapping("/read/{notificationId}")
    public List<NotificationDTO> read(@PathVariable int notificationId) throws Exception {
        return notificationService.read(notificationId).stream().map(NotificationDTO::new).toList();
    }

    @GetMapping("/generate-notification")
    public NotificationDTO generateNotification() throws Exception {
        User user = userService.getByUsername("movavi");
        if (user == null) {
            throw new RuntimeException("User with ID 1 not found");
        }

        Notification notification = new Notification();
        notification.setHeader("Random Notification " + new Random().nextInt(100));
        notification.setText("This is a randomly generated notification.");
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUser(user);

        return new NotificationDTO(notificationService.create(notification));
    }
}
