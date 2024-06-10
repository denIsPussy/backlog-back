package com.onlineshop.onlineshop.Models.Database.User;

import com.onlineshop.onlineshop.Models.DTO.Notification.NotificationUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

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

    @Column(name = "isRead")
    @NotNull
    private boolean isRead = false;

    @Column(name = "isSystem")
    @NotNull
    private boolean isSystem = false;

    @Column(name = "createdAt")
    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id") // Это указывает, что столбец 'user_id' используется для присоединения к таблице User
    private User user;

    public Notification(){

    }

    public Notification(NotificationUpdateDTO notificationUpdateDTO) {
        this.id = notificationUpdateDTO.getId();
        this.isRead = notificationUpdateDTO.isRead();
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

    public LocalDateTime getDate() {
        return createdAt;
    }

    public void setDate(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }
}
