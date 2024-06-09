package com.onlineshop.onlineshop.Services;
import com.onlineshop.onlineshop.ApiService;
import com.onlineshop.onlineshop.Controllers.AuthRequest;
import com.onlineshop.onlineshop.Exceptions.CustomExceptions.AuthenticationFailureException;
import com.onlineshop.onlineshop.JwtUtil;
import com.onlineshop.onlineshop.Models.DTO.ConfirmationCodeDTO;
import com.onlineshop.onlineshop.Models.DTO.PasswordUpdateDTO;
import com.onlineshop.onlineshop.Models.DTO.UpdateSettingsDTO;
import com.onlineshop.onlineshop.Models.DTO.UserUpdateDTO;
import com.onlineshop.onlineshop.Models.EverythingElse.TwoFactorCodeDTO;
import com.onlineshop.onlineshop.Models.EverythingElse.User;
import com.onlineshop.onlineshop.Models.vk.ApiResponse;
import com.onlineshop.onlineshop.Models.vk.UserTokenDto;
import com.onlineshop.onlineshop.Models.vk.VkUserPartialDto;
import com.onlineshop.onlineshop.PasswordGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
@Service
@Transactional
public class AuthService{
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ApiService apiService;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Async("taskExecutor")
    public CompletableFuture<ApiResponse> exchangeAndRetrieveProfile(String silentToken, String uuid) {
        return apiService.exchangeSilentAuthToken(silentToken, uuid)
                .thenCompose(vkApiResponse -> {
                    User user = userService.getByVkId(vkApiResponse.getResponse().getUserId());
                    if (user != null) {
                        logger.info("Username : {}",
                                user.getUsername());
                        return authenticateUser(new AuthRequest(vkApiResponse.getResponse().getUserId()));
                    }
                    return apiService.getProfileInfo(vkApiResponse.getResponse().getAccessToken())
                            .thenApply(profileInfo -> new VkUserPartialDto(
                                    vkApiResponse.getResponse().getUserId(),
                                    profileInfo.getResponse().get(0).getFirstName(),
                                    profileInfo.getResponse().get(0).getLastName(),
                                    true,
                                    "User data"
                            ));
                })
                .exceptionally(e -> {
                    throw new CompletionException(new AuthenticationFailureException(e.getMessage()));
                });
    }

    @Async("taskExecutor")
    public CompletableFuture<ApiResponse> authenticateUser(AuthRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UserDetails userDetails;
                User user;

                if (request.getVkId() != 0) {
                    // Аутентификация по vkId
                    user = userService.getByVkId(request.getVkId());
                    if (user == null) throw new UsernameNotFoundException("Пользователь с таким vkId не найден.");
                } else {
                    // Стандартная аутентификация по имени пользователя и паролю
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
                    user = userService.getByUsername(request.getUsername());
                    if (user == null) throw new UsernameNotFoundException("Пользователь с таким именем не найден.");

                    logger.info("Authenticated user: {}", user.getUsername());

                    if (user.isTwoFactorEnabled()) {
                        generateAndSend2FACode(user.getUsername());
                        return new ApiResponse(true,"Код 2FA отправлен на ваш электронный адрес. Пожалуйста, подтвердите, чтобы завершить авторизацию."){};
                    }
                }
                userDetails = userService.loadUserByUsername(user.getUsername());
                return new UserTokenDto(jwtUtil.generateToken(userDetails), user.getUsername(), true, "token", user.isChildModeEnabled());
            } catch (Exception e) {
                throw new CompletionException(new AuthenticationFailureException(e.getMessage()));
            }
        });
    }

    public ApiResponse validateAndGenerateJwt(TwoFactorCodeDTO twoFactorCodeDTO) {
        User user = userService.getByUsername(twoFactorCodeDTO.getUsername());
        if (verify2FACode(twoFactorCodeDTO.getCode(), user)) {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
            return new UserTokenDto(jwtUtil.generateToken(userDetails), user.getUsername(), true, "2FA code", user.isChildModeEnabled());
        } else {
            return new ApiResponse(false, "Неверный 2FA код"){};
        }
    }

    public boolean verify2FACode(String code, User user){
        return user.getConfirmationCode().equals(code) &&
                user.getConfirmationCodeExpiration().isAfter(LocalDateTime.now());
    }

    public void generateAndSend2FACode(String username) throws Exception {
        String code = String.format("%06d", new Random().nextInt(999999));
        User findUser = userService.getByUsername(username);
        findUser.setConfirmationCode(code);
        findUser.setConfirmationCodeExpiration(LocalDateTime.now().plusMinutes(10));
        try{
            userService.update(findUser);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        emailService.sendSimpleMessage(findUser.getEmail(), "Your 2FA Code", "Your code is: " + code);
    }

    public ApiResponse resetPassword() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        String newPassword = PasswordGenerator.generatePassword(8);
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.update(user);
        emailService.sendSimpleMessage(user.getEmail(), "Сброс пароля", "Ваш новый пароль: " + newPassword + ". Вы можете сменить пароль в личном профиле.");
        return new ApiResponse(true, "Пароль успешно сброшен. Ожидайте уведомления на почту"){};
    }

//    public ApiResponse confirmationAction(ConfirmationCodeDTO confirmationCodeDTO) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userService.getByUsername(userDetails.getUsername());
//    }
}
