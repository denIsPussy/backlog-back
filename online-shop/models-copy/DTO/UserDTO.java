package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.ShoppingCart;
import com.onlineshop.onlineshop.Models.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String username;
    private String password;
    private String email;
    private boolean isTwoFactorEnabled;
    private String twoFactorCode;
    private LocalDateTime twoFactorExpiration;
    private float deposit;
    private boolean isChildModeEnabled;
    private boolean areNotificationsEnabled;
    private ShoppingCart shoppingCart;
    private List<OrderDTO> orderList;
    private List<NotificationDTO> notificationList;

    public UserDTO(){

    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.deposit = user.getDeposit();
        this.isTwoFactorEnabled = user.isTwoFactorEnabled();
        this.twoFactorCode = user.getTwoFactorCode();
        this.twoFactorExpiration = user.getTwoFactorExpiration();
        this.isChildModeEnabled = user.isChildModeEnabled();
        this.areNotificationsEnabled = user.isAreNotificationsEnabled();
        this.shoppingCart = user.getShoppingCart();
        this.orderList = user.getOrderList().stream().map(OrderDTO::new).toList();
        this.notificationList = user.getNotificationList().stream().map(NotificationDTO::new).toList();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public float getDeposit() {
        return deposit;
    }

    public String getEmail() {
        return email;
    }

    public boolean isTwoFactorEnabled() {
        return isTwoFactorEnabled;
    }

    public String getTwoFactorCode() {
        return twoFactorCode;
    }

    public LocalDateTime getTwoFactorExpiration() {
        return twoFactorExpiration;
    }

    public boolean isChildModeEnabled() {
        return isChildModeEnabled;
    }

    public boolean isAreNotificationsEnabled() {
        return areNotificationsEnabled;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public List<OrderDTO> getOrderList() {
        return orderList;
    }

    public List<NotificationDTO> getNotificationList() {
        return notificationList;
    }
}
