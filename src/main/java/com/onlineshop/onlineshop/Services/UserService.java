package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.DTO.User.SignUpDTO;
import com.onlineshop.onlineshop.Models.EverythingElse.Order;
import com.onlineshop.onlineshop.Models.EverythingElse.ShoppingCart;
import com.onlineshop.onlineshop.Models.EverythingElse.User;
import com.onlineshop.onlineshop.Repositories.UserRepository;
import com.onlineshop.onlineshop.Models.vk.VkApiResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
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

//    public Mono<vkProfileInfo> exchangeAndRetrieveProfile(String silentToken, int uuid) {
//        return apiService.exchangeSilentAuthToken(silentToken, uuid)
//                .flatMap(vkApiResponse -> apiService.getProfileInfo(vkApiResponse.getAccessToken()));
//    }

//    private AuthResponse authenticateAfterRegistration(SignUpDTO signUpDTO) {
//        AuthRequest request = new AuthRequest(signUpDTO.getUsername(), signUpDTO.getPassword());
//        return authService.authenticateUser(request);
//    }

//    public AuthenticationResponse authenticateUser(AuthenticationRequest request) throws AuthenticationException {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//        final User user = getByUsername(request.getUsername());
//        if (user.isTwoFactorEnabled()) {
//            generateAndSend2FACode(user.getUsername());
//            return new AuthenticationResponse(null, "2FA code sent to your email. Please verify to complete login.");
//        }
//        final UserDetails userDetails = loadUserByUsername(user.getUsername());
//        final String jwt = jwtUtil.generateToken(userDetails);
//        return new AuthenticationResponse(jwt, null);
//    }
//
//    public String validateAndGenerateJwt(TwoFactorCodeDTO twoFactorCodeDTO) {
//        User user = getByUsername(twoFactorCodeDTO.getUsername());
//        if (verify2FACode(twoFactorCodeDTO.getCode(), user)) {
//            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
//            return jwtUtil.generateToken(userDetails);
//        } else {
//            return "Invalid 2FA code";
//        }
//    }

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

//    public boolean verify2FACode(String code, User user){
//        return user.getTwoFactorCode().equals(code) &&
//                user.getTwoFactorExpiration().isAfter(LocalDateTime.now());
//    }
//
//    public void generateAndSend2FACode(String username) {
//        String code = String.format("%06d", new Random().nextInt(999999));
//        User findUser = getByUsername(username);
//        findUser.setTwoFactorCode(code);
//        findUser.setTwoFactorExpiration(LocalDateTime.now().plusMinutes(10));
//        update(findUser);
//        emailService.sendSimpleMessage(findUser.getEmail(), "Your 2FA Code", "Your code is: " + code);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
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
}
