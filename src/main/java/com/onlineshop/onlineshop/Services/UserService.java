package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Exceptions.CustomExceptions.InvalidRequestException;
import com.onlineshop.onlineshop.Exceptions.CustomExceptions.RegistrationFailureException;
import com.onlineshop.onlineshop.Models.DTO.User.PasswordUpdateDTO;
import com.onlineshop.onlineshop.Models.DTO.User.UpdateSettingsDTO;
import com.onlineshop.onlineshop.Models.DTO.User.SignUpDTO;
import com.onlineshop.onlineshop.Models.DTO.User.UserUpdateDTO;
import com.onlineshop.onlineshop.Models.Database.Order.Order;
import com.onlineshop.onlineshop.Models.Database.ShoppingCart.ShoppingCart;
import com.onlineshop.onlineshop.Models.Database.User.User;
import com.onlineshop.onlineshop.Models.Database.Product.Product;
import com.onlineshop.onlineshop.Models.Database.Product.Review;
import com.onlineshop.onlineshop.Models.DTO.Vk.ApiResponse;
import com.onlineshop.onlineshop.Repositories.UserRepository;
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
    @Autowired
    private ProductService productService;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public void registerUser(SignUpDTO signUpDTO) {
        User newUser = new User();
        if (userRepository.findByUsername(signUpDTO.getUsername()).isPresent()) {
            throw new RegistrationFailureException("Пользователь с таким логином уже существует");
        }
        else if (userRepository.findByEmail(signUpDTO.getEmail()) != null){
            throw new RegistrationFailureException("Пользователь с такой почтой уже существует");
        }
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
        userRepository.save(newUser);
    }

    public ShoppingCart getShopCartByUsername(String username) throws Exception {
        Optional<User> findUser = userRepository.findByUsername(username);
        User user = findUser.orElseThrow(() -> new Exception("Не удалось загрузить данные корзины"));
        return user.getShoppingCart();
    }

    public List<Order> getOrders(String username) throws Exception {
        Optional<User> findUser = userRepository.findByUsername(username);
        User user = findUser.orElseThrow(() -> new Exception("Не удалось загрузить заказы"));
        return user.getOrderList();
    }

    public void update(User user) throws Exception {
        try{
            Optional<User> optUser = userRepository.findByUsername(user.getUsername());
            User findUser = optUser.orElseThrow(() -> new UsernameNotFoundException("Не удалось обновить данные"));
            findUser.update(user);
            userRepository.save(findUser);
        }
        catch (UsernameNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new Exception("Не удалось обновить данные");
        }
    }

    public User getByUsername(String username) throws Exception {
        try{
            Optional<User> optUser = userRepository.findByUsername(username);
            return optUser.orElseThrow();
        }
        catch (Exception e){
            throw new Exception("Произошла ошибка. Попробуйте позже.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public ApiResponse changeUserData(UserUpdateDTO userUpdateDTO) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Не удалось обновить данные"));
        if (passwordEncoder.matches(userUpdateDTO.getPassword(), user.getPassword())) {
            user.setFirstName(userUpdateDTO.getFirstname());
            user.setLastName(userUpdateDTO.getLastname());
            user.setPatronymic(userUpdateDTO.getPatronymic());
            update(user);
            return new ApiResponse(true, "Данные успешно изменены"){};
        }
        throw new InvalidRequestException("Неверный пароль");

    }

    public ApiResponse changePassword(PasswordUpdateDTO passwordUpdateDTO) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Не удалось обновить пароль"));
        if (passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
            update(user);
            return new ApiResponse(true, "Пароль успешно изменен"){};
        }
        throw new InvalidRequestException("Неверный пароль");
    }

    public ApiResponse settingChildMode(UpdateSettingsDTO updateSettingsDTO) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Не удалось настроить детский режим"));
        if (passwordEncoder.matches(updateSettingsDTO.getPassword(), user.getPassword())) {
            ApiResponse apiResponse = new ApiResponse(true, ""){};
            if (user.isChildModeEnabled()) apiResponse.setMessage("Детский режим выключен");
            else apiResponse.setMessage("Детский режим включен");
            user.setChildModeEnabled(!user.isChildModeEnabled());
            update(user);
            return apiResponse;
        }
        throw new InvalidRequestException("Неверный пароль");
    }

    public ApiResponse settingTwoFactorAuth(UpdateSettingsDTO updateSettingsDTO) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Не удалось настроить двухэтапную аутентификацию"));

        if (passwordEncoder.matches(updateSettingsDTO.getPassword(), user.getPassword())) {
            ApiResponse apiResponse = new ApiResponse(true, ""){};
            if (user.isTwoFactorEnabled()) apiResponse.setMessage("Двухэтапная аутентификация выключена");
            else apiResponse.setMessage("Двухэтапная аутентификация включена");

            user.setTwoFactorEnabled(!user.isTwoFactorEnabled());
            update(user);
            return apiResponse;
        }
        throw new InvalidRequestException("Неверный пароль");
    }

    public void addBonuses(User user, int bonus){
        return;
    }

    public void writeOffBonuses(User user, int bonus){
        return;
    }

    public User getByVkId(int vkId) {
        Optional<User> optUser = userRepository.findByVkId(vkId);
        return optUser.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public ApiResponse settingNotifications(UpdateSettingsDTO updateSettingsDTO) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Не удалось настроить уведомления"));

        if (passwordEncoder.matches(updateSettingsDTO.getPassword(), user.getPassword())) {
            ApiResponse apiResponse = new ApiResponse(true, ""){};
            if (user.isAreNotificationsEnabled()) apiResponse.setMessage("Рассылки выключены");
            else apiResponse.setMessage("Рассылки включены");

            user.setAreNotificationsEnabled(!user.isAreNotificationsEnabled());
            update(user);
            return apiResponse;
        }
        throw new InvalidRequestException("Неверный пароль");
    }

    public ApiResponse containsInCart(int productId) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = getByUsername(userDetails.getUsername());
        boolean isContain = user.getShoppingCart().getCartItems().stream()
                .anyMatch(item -> item.getProduct().getId() == productId);
        if (isContain) {
            return new ApiResponse(true, "Cart contain the product") {};
        } else {
            return new ApiResponse(false, "Cart not contain the product") {};
        }
    }

    public ApiResponse checkingForReview(int productId) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = getByUsername(userDetails.getUsername());
        Product product = productService.getById(productId);
        List<Review> reviews = product.getReviewList();
        Optional<Review> optionalReview = reviews.stream()
                .filter(item -> item.getUser().getId() == user.getId())
                .findFirst();

        if (optionalReview.isPresent()) {
            return new ApiResponse(true, String.valueOf(optionalReview.get().getId())){};
        } else {
            return new ApiResponse(true, "0"){};
        }
    }

    public ApiResponse topUpDeposit(int amount) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = getByUsername(userDetails.getUsername());
        if (user == null){
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        user.setDeposit(user.getDeposit() + amount);
        return new ApiResponse(true,"Баланс пополнен"){};
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
