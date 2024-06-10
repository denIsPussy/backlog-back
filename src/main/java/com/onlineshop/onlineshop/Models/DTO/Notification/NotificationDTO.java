package com.onlineshop.onlineshop.Models.DTO.Notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onlineshop.onlineshop.Models.Database.User.Notification;

public class NotificationDTO {
    private int id;
    private String header;
    private String text;
    @JsonProperty("isRead")
    private boolean isRead;

    public NotificationDTO(){

    }

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.header = notification.getHeader();
        this.text = notification.getText();
        this.isRead = notification.isRead();
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    public boolean isRead() {
        return isRead;
    }
}
