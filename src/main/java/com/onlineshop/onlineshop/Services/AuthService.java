package com.onlineshop.onlineshop.Services;
import com.onlineshop.onlineshop.ApiService;
import com.onlineshop.onlineshop.Controllers.AuthRequest;
import com.onlineshop.onlineshop.Controllers.AuthResponse;
import com.onlineshop.onlineshop.JwtUtil;
import com.onlineshop.onlineshop.Models.TwoFactorCodeDTO;
import com.onlineshop.onlineshop.Models.User;
import com.onlineshop.onlineshop.Models.vk.vkProfileInfoDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
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
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public Mono<vkProfileInfoDTO> exchangeAndRetrieveProfile(String silentToken, String uuid) {
        return apiService.exchangeSilentAuthToken(silentToken, uuid)
                .doOnError(e -> logger.error("Error during exchangeSilentAuthToken: silentToken: {}, uuid: {}", silentToken, uuid, e))
                .flatMap(vkApiResponse -> {
                    // Логирование деталей vkApiResponse
                    logger.info("Received VkApiResponse: AccessToken: {}, UserId: {}",
                            vkApiResponse.getResponse().getAccessToken(), vkApiResponse.getResponse().getUserId());

                    User user = userService.getByVkId(vkApiResponse.getResponse().getUserId());
                    if (user != null) return Mono.just(vkProfileInfoDTO.getAuthData(-1, user.getFirstName(), user.getLastName()));

                    // После получения access token, получаем информацию профиля
                    return apiService.getProfileInfo(vkApiResponse.getResponse().getAccessToken())
                            .doOnError(e -> logger.error("Error during getProfileInfo: AccessToken: {}", vkApiResponse.getResponse().getAccessToken(), e))
                            .map(profileInfo -> {
                                // Логирование полученной информации о профиле пользователя
                                logger.info("Profile Info: UserId: {}, FirstName: {}",
                                        vkApiResponse.getResponse().getUserId(), profileInfo.getResponse().get(0).getFirstName());

                                // Формирование DTO и возврат
                                return vkProfileInfoDTO.getRegisterData(profileInfo, vkApiResponse.getResponse().getUserId());
                            });
                })
                .doOnError(e -> logger.error("Error in exchangeAndRetrieveProfile", e))
                .onErrorResume(e -> {
                    // В случае ошибки возвращаем пустой Mono или другой обработчик
                    return Mono.empty();
                });
    }

    public AuthResponse authenticateUser(AuthRequest request) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final User user = userService.getByUsername(request.getUsername());
        if (user.isTwoFactorEnabled()) {
            generateAndSend2FACode(user.getUsername());
            return AuthResponse.withMessage("2FA code sent to your email. Please verify to complete login.");
        }
        final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return AuthResponse.withJwt(jwt);
    }

    public String validateAndGenerateJwt(TwoFactorCodeDTO twoFactorCodeDTO) {
        User user = userService.getByUsername(twoFactorCodeDTO.getUsername());
        if (verify2FACode(twoFactorCodeDTO.getCode(), user)) {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
            return jwtUtil.generateToken(userDetails);
        } else {
            return "Invalid 2FA code";
        }
    }

    public boolean verify2FACode(String code, User user){
        return user.getTwoFactorCode().equals(code) &&
                user.getTwoFactorExpiration().isAfter(LocalDateTime.now());
    }

    public void generateAndSend2FACode(String username) {
        String code = String.format("%06d", new Random().nextInt(999999));
        User findUser = userService.getByUsername(username);
        findUser.setTwoFactorCode(code);
        findUser.setTwoFactorExpiration(LocalDateTime.now().plusMinutes(10));
        userService.update(findUser);
        emailService.sendSimpleMessage(findUser.getEmail(), "Your 2FA Code", "Your code is: " + code);
    }
}
