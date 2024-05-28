package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.SignUpDTO;
import com.onlineshop.onlineshop.Models.DTO.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstname")
    @NotNull
    private String firstName;

    @Column(name = "lastname")
    @NotNull
    private String lastName;

    @Column(name = "patronymic")
    @NotNull
    private String patronymic;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "isTwoFactorEnabled")
    @NotNull
    private boolean isTwoFactorEnabled = true;

    @Column(name = "twoFactorCode")
    private String twoFactorCode;

    @Column(name = "twoFactorExpiration")
    private LocalDateTime twoFactorExpiration;

    @Column(name = "deposit")
    @NotNull
    private float deposit = 0.f;

    @Column(name = "isChildModeEnabled")
    @NotNull
    private boolean isChildModeEnabled = false;

    @Column(name = "areNotificationsEnabled")
    @NotNull
    private boolean areNotificationsEnabled = true;

    @JoinColumn(name = "cart_id")
    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart = new ShoppingCart();

    @JoinColumn(name = "user_id")
    @OneToMany()
    private List<Order> orderList = new ArrayList<>();

    @JoinColumn(name = "user_id")
    @OneToMany()
    private List<Notification> notificationList = new ArrayList<>();

    public User(){

    }

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.patronymic = userDTO.getPatronymic();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.email = userDTO.getEmail();
        this.isTwoFactorEnabled = userDTO.isTwoFactorEnabled();
        this.twoFactorCode = userDTO.getTwoFactorCode();
        this.twoFactorExpiration = userDTO.getTwoFactorExpiration();
        this.deposit = userDTO.getDeposit();
        this.isChildModeEnabled = userDTO.isChildModeEnabled();
        this.areNotificationsEnabled = userDTO.isAreNotificationsEnabled();
        this.shoppingCart = userDTO.getShoppingCart();
        this.orderList = userDTO.getOrderList().stream().map(Order::new).toList();
        this.notificationList = userDTO.getNotificationList().stream().map(Notification::new).toList();
    }

    public User (SignUpDTO signUpDTO) {
        this.firstName = signUpDTO.getFirstName();
        this.lastName = signUpDTO.getLastName();
        this.patronymic = signUpDTO.getPatronymic();
        this.username = signUpDTO.getUsername();
        this.password = signUpDTO.getPassword();
        this.email = signUpDTO.getEmail();
    }

    public void update(User user) {
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
        this.orderList = user.getOrderList();
        this.notificationList = user.getNotificationList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTwoFactorEnabled() {
        return isTwoFactorEnabled;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        isTwoFactorEnabled = twoFactorEnabled;
    }

    public String getTwoFactorCode() {
        return twoFactorCode;
    }

    public void setTwoFactorCode(String twoFactorCode) {
        this.twoFactorCode = twoFactorCode;
    }

    public LocalDateTime getTwoFactorExpiration() {
        return twoFactorExpiration;
    }

    public void setTwoFactorExpiration(LocalDateTime twoFactorExpiration) {
        this.twoFactorExpiration = twoFactorExpiration;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public boolean isChildModeEnabled() {
        return isChildModeEnabled;
    }

    public void setChildModeEnabled(boolean childModeEnabled) {
        isChildModeEnabled = childModeEnabled;
    }

    public boolean isAreNotificationsEnabled() {
        return areNotificationsEnabled;
    }

    public void setAreNotificationsEnabled(boolean areNotificationsEnabled) {
        this.areNotificationsEnabled = areNotificationsEnabled;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void addToOrderList(Order order) {
        this.orderList.add(order);
    }

    public void removeFromToOrderList(Order order) {
        this.orderList.removeIf(item -> item.getId() == order.getId());
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public void addToNotificationList(Notification notification) {
        this.notificationList.add(notification);
    }

    public void removeFromNotificationList(Notification notification) {
        this.notificationList.removeIf(item -> item.getId() == notification.getId());
    }
}