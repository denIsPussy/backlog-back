package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.DTO.PasswordUpdateDTO;
import com.onlineshop.onlineshop.Models.DTO.UpdateSettingsDTO;
import com.onlineshop.onlineshop.Models.DTO.User.SignUpDTO;
import com.onlineshop.onlineshop.Models.DTO.UserUpdateDTO;
import com.onlineshop.onlineshop.Models.EverythingElse.Order;
import com.onlineshop.onlineshop.Models.EverythingElse.ShoppingCart;
import com.onlineshop.onlineshop.Models.EverythingElse.User;
import com.onlineshop.onlineshop.Models.vk.ApiResponse;
import com.onlineshop.onlineshop.Repositories.UserRepository;
import com.onlineshop.onlineshop.Models.vk.VkApiResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public String registerUser(SignUpDTO signUpDTO) {
        User newUser = new User();
        if (signUpDTO.getVkId() != 0) {
            newUser.setVkId(signUpDTO.getVkId());
            newUser.setTwoFactorEnabled(false);
        }
        newUser.setEmail(signUpDTO.getEmail());
        newUser.setFirstName(signUpDTO.getFirstName());
        newUser.setLastName(signUpDTO.getLastName());
        newUser.setPatronymic(signUpDTO.getPatronymic());
        newUser.setUsername(signUpDTO.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(signUpDTO.getPassword()));
        try{
            userRepository.save(newUser);
            return "Регистрация прошла успешно";
        }
        catch (Exception e){
            return "Что-то пошло не так. Попробуйте позже";
        }
    }

    public String create(User user){
        try{
            userRepository.save(user);
            return "Регистрация прошла успешно";
        }
        catch (Exception e){
            return "Что-то пошло не так. Попробуйте позже";
        }
    }

    public String register(VkApiResponse vkApiResponse){
        try{
            User user = new User();
            user.setVkId(vkApiResponse.getResponse().getUserId());
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
            logger.info(user.getShoppingCart().getCartItems().toString());
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
        try{
            Optional<User> optUser = userRepository.findByUsername(user.getUsername());
            User findUser = optUser.orElseThrow();
            if (findUser == null) return "Пользователь не найден";
            findUser.update(user);
            userRepository.save(findUser);
            return "Данные пользователя успешно обновлены";
        }
        catch (Exception e){
            return "Что-то пошло не так. Попробуйте позже";
        }
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
        return "";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public ApiResponse changeUserData(UserUpdateDTO userUpdateDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        if (passwordEncoder.matches(userUpdateDTO.getPassword(), user.getPassword())) {
            user.setFirstName(userUpdateDTO.getFirstname());
            user.setLastName(userUpdateDTO.getLastname());
            user.setPatronymic(userUpdateDTO.getPatronymic());
            update(user);
            return new ApiResponse(true, "Данные успешно изменены"){};
        }
        return new ApiResponse(false, "Неверный пароль"){};

    }

    public ApiResponse changePassword(PasswordUpdateDTO passwordUpdateDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        if (passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordUpdateDTO.getNewPassword());
            update(user);
            return new ApiResponse(true, "Пароль успешно изменен"){};
        }
        return new ApiResponse(false, "Неверный прошлый пароль"){};
    }

    public ApiResponse settingChildMode(UpdateSettingsDTO updateSettingsDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        if (passwordEncoder.matches(updateSettingsDTO.getPassword(), user.getPassword())) {
            ApiResponse apiResponse = new ApiResponse(true, ""){};
            if (user.isChildModeEnabled()) apiResponse.setMessage("Детский режим выключен");
            else apiResponse.setMessage("Детский режим включен");
            user.setChildModeEnabled(!user.isChildModeEnabled());
            update(user);
            return apiResponse;
        }
        return new ApiResponse(false, "Неверный пароль"){};
    }

    public ApiResponse settingTwoFactorAuth(UpdateSettingsDTO updateSettingsDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        if (passwordEncoder.matches(updateSettingsDTO.getPassword(), user.getPassword())) {
            ApiResponse apiResponse = new ApiResponse(true, ""){};
            if (user.isTwoFactorEnabled()) apiResponse.setMessage("Двухэтапная аутентификация выключена");
            else apiResponse.setMessage("Двухэтапная аутентификация включена");

            user.setTwoFactorEnabled(!user.isTwoFactorEnabled());
            update(user);
            return apiResponse;
        }
        return new ApiResponse(false, "Неверный пароль"){};
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

    public User getByVkId(int vkId) {
        try{
            Optional<User> optUser = userRepository.findByVkId(vkId);
            return optUser.orElseThrow();
        }
        catch (Exception e){
            return null;
        }
    }

    public ApiResponse settingNotifications(UpdateSettingsDTO updateSettingsDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        if (passwordEncoder.matches(updateSettingsDTO.getPassword(), user.getPassword())) {
            ApiResponse apiResponse = new ApiResponse(true, ""){};
            if (user.isAreNotificationsEnabled()) apiResponse.setMessage("Рассылки выключены");
            else apiResponse.setMessage("Рассылки включены");

            user.setAreNotificationsEnabled(!user.isAreNotificationsEnabled());
            update(user);
            return apiResponse;
        }
        return new ApiResponse(false, "Неверный пароль"){};
    }
}
