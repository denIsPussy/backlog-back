package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.Order;
import com.onlineshop.onlineshop.Models.ShoppingCart;
import com.onlineshop.onlineshop.Models.User;
import com.onlineshop.onlineshop.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public String create(User user){
        try{
            userRepository.save(user);
            return "Регистрация прошла успешно";
        }
        catch (Exception e){
            return "Что-то пошло не так. Попробуйте позже";
        }
    }

    public ShoppingCart getShopCartByUsername(String username){
        try{
            Optional<User> findUser = userRepository.findByUsername(username);
            User user = findUser.orElseThrow();
            return user.getShoppingCart();
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Order> getOrders(String username){
        try{
            Optional<User> findUser = userRepository.findByUsername(username);
            User user = findUser.orElseThrow();
            return user.getOrderList();
        }
        catch (Exception e){
            return null;
        }
    }

    public String update(User user){
//        try{
//            User updatedUser = userRepository.findByUsername(user.getUsername());
//            if (updatedUser == null) return "Пользователь не найден";
//            updatedUser.update(user);
//            userRepository.save(updatedUser);
//            return "Данные пользователя успешно обновлены";
//        }
//        catch (Exception e){
//            return "Что-то пошло не так. Попробуйте позже";
//        }
        return "";
    }

    public boolean verifyUserCredentials(User user){
        return true;
    }

    public User getByUsername(String username){
        try{
            Optional<User> optUser = userRepository.findByUsername(username);
            return optUser.orElseThrow();
        }
        catch (Exception e){
            return null;
        }
    }

    public String authorization(String login, String password){
//        try{
//            Optional<User> optUser = userRepository.findByUsername(login);
//
//            if (findUser == null) return "Пользователь не найден";
//            else if (findUser.getPassword().equals(password)) return "Авторизация прошла успешно";
//            return "Неверные данные";
//        }
//        catch (Exception e){
//            return "Что-то пошло не так. Попробуйте позже";
//        }
        return "";
    }

    public void addBonuses(User user, int bonus){
        return;
    }

    public void writeOffBonuses(User user, int bonus){
        return;
    }

    public void setChildMode(boolean isEnabled){
    }

    public void configureNotifications(boolean isEnabled){
    }

    public void passwordEncryption(String password){
    }

    public void decryptingPassword(String password){
    }

    public boolean verify2FACode(String code, User user){
        return user.getTwoFactorCode().equals(code) &&
                user.getTwoFactorExpiration().isAfter(LocalDateTime.now());
    }

    public void generateAndSend2FACode(String username) {
        String code = String.format("%06d", new Random().nextInt(999999));
        User findUser = getByUsername(username);
        findUser.setTwoFactorCode(code);
        findUser.setTwoFactorExpiration(LocalDateTime.now().plusMinutes(10));
        update(findUser);
        emailService.sendSimpleMessage(findUser.getEmail(), "Your 2FA Code", "Your code is: " + code);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
