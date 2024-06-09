package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.EverythingElse.Notification;
import com.onlineshop.onlineshop.Models.EverythingElse.Notification;

public class NotificationUpdateDTO {
    private int id;
    private boolean isRead;
    private boolean isSystem;

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

    public boolean isSystem() {
        return isSystem;
    }
}
