package com.onlineshop.onlineshop.Services;
import com.onlineshop.onlineshop.ApiResponse1;
import com.onlineshop.onlineshop.ApiService;
import com.onlineshop.onlineshop.Controllers.AuthRequest;
import com.onlineshop.onlineshop.Exceptions.CustomExceptions.AuthenticationFailureException;
import com.onlineshop.onlineshop.JwtUtil;
import com.onlineshop.onlineshop.Models.TwoFactorCodeDTO;
import com.onlineshop.onlineshop.Models.User;
import com.onlineshop.onlineshop.Models.vk.ApiResponse;
import com.onlineshop.onlineshop.Models.vk.UserTokenDto;
import com.onlineshop.onlineshop.Models.vk.VkUserPartialDto;
import com.onlineshop.onlineshop.ResponseMessageDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

//    public Mono<ApiResponse<?>> exchangeAndRetrieveProfile(String silentToken, String uuid) {
//        return apiService.exchangeSilentAuthToken(silentToken, uuid)
//                .flatMap(vkApiResponse -> {
//                    User user = userService.getByVkId(vkApiResponse.getResponse().getUserId());
//                    if (user != null) {
//                        return authenticateUser(new AuthRequest(user.getUsername(), user.getPassword()))
//                                .flatMap(jwtResponse -> Mono.just(ApiResponse.withData(jwtResponse))); // Убедитесь, что jwtResponse уже содержит тип VkAuthDto или другой подходящий тип
//                    }
//                    return apiService.getProfileInfo(vkApiResponse.getResponse().getAccessToken())
//                            .map(profileInfo -> ApiResponse.withData(new VkAuthDto(
//                                    vkApiResponse.getResponse().getUserId(),
//                                    profileInfo.getResponse().get(0).getFirstName(),
//                                    profileInfo.getResponse().get(0).getLastName(),
//                                    false
//                            )))
//                            .onErrorResume(e -> Mono.error(new AuthenticationFailureException(e.getMessage())));
//                })
//                .onErrorResume(e -> Mono.error(new AuthenticationFailureException(e.getMessage())));
//    }

    @Async("taskExecutor")
    public CompletableFuture<ApiResponse> exchangeAndRetrieveProfile(String silentToken, String uuid) {
        return apiService.exchangeSilentAuthToken(silentToken, uuid)
                .thenCompose(vkApiResponse -> {
                    User user = userService.getByVkId(vkApiResponse.getResponse().getUserId());
                    if (user != null) {
                        return authenticateUser(new AuthRequest(user.getUsername(), user.getPassword()))
                                .thenApply(jwtToken -> jwtToken);
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
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
                final User user = userService.getByUsername(request.getUsername());
                if (user == null) throw new UsernameNotFoundException("Пользователь с таким именем не найден.");
                if (user.isTwoFactorEnabled()) {
                    generateAndSend2FACode(user.getUsername());
                    return new ApiResponse(true,"Код 2FA отправлен на ваш электронный адрес. Пожалуйста, подтвердите, чтобы завершить авторизацию.") {
                    };
                }
                final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
                return new UserTokenDto(jwtUtil.generateToken(userDetails), user.getUsername(), true, "token");
            } catch (Exception e) {
                throw new CompletionException(new AuthenticationFailureException(e.getMessage()));
            }
        });
    }




//    public Mono<ApiResponse<String>> authenticateUser(AuthRequest request) {
//        return Mono.fromCallable(() -> {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//            final User user = userService.getByUsername(request.getUsername());
//            if (user == null) throw new UsernameNotFoundException("Пользователь с таким именем не найден.");
//            if (user.isTwoFactorEnabled()) {
//                generateAndSend2FACode(user.getUsername());
//                return ApiResponse.<String>withMessage("Код 2FA отправлен на ваш электронный адрес. Пожалуйста, подтвердите, чтобы завершить авторизацию.");
//            }
//            final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
//            final String jwt = jwtUtil.generateToken(userDetails);
//            return ApiResponse.withData(jwt);
//        }).onErrorMap(e -> new AuthenticationFailureException(e.getMessage()));
//    }



    public ApiResponse validateAndGenerateJwt(TwoFactorCodeDTO twoFactorCodeDTO) {
        User user = userService.getByUsername(twoFactorCodeDTO.getUsername());
        if (verify2FACode(twoFactorCodeDTO.getCode(), user)) {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
            return new UserTokenDto(jwtUtil.generateToken(userDetails), user.getUsername(), true, "2FA code");
        } else {
            return new ApiResponse(false, "Неверный 2FA код"){};
        }
    }

    public boolean verify2FACode(String code, User user){
        return user.getTwoFactorCode().equals(code) &&
                user.getTwoFactorExpiration().isAfter(LocalDateTime.now());
    }

    public void generateAndSend2FACode(String username) throws Exception {
        String code = String.format("%06d", new Random().nextInt(999999));
        User findUser = userService.getByUsername(username);
        findUser.setTwoFactorCode(code);
        findUser.setTwoFactorExpiration(LocalDateTime.now().plusMinutes(10));
        try{
            userService.update(findUser);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        emailService.sendSimpleMessage(findUser.getEmail(), "Your 2FA Code", "Your code is: " + code);
    }
}
