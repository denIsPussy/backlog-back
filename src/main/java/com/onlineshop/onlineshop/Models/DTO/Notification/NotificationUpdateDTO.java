package com.onlineshop.onlineshop.Models.DTO.Notification;

import com.onlineshop.onlineshop.Models.Database.User.Notification;

public class NotificationUpdateDTO {
    private int id;
    private boolean isRead;

    public NotificationUpdateDTO(){

    }

    public NotificationUpdateDTO(Notification notification) {
        this.id = notification.getId();
        this.isRead = notification.isRead();
    }

    public int getId() {
        return id;
    }

    public boolean isRead() {
        return isRead;
    }
}
