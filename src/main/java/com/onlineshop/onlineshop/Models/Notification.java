package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.NotificationDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "header")
    @NotNull
    private String header;

    @Column(name = "text")
    @NotNull
    private String text;

    @Column(name = "date")
    @NotNull
    private String date;

    public Notification(){

    }

    public Notification(NotificationDTO notificationDTO) {
        this.id = notificationDTO.getId();
        this.header = notificationDTO.getHeader();
        this.text = notificationDTO.getText();
        this.date = notificationDTO.getDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
