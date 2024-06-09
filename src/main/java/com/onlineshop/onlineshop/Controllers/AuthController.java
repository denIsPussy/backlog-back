package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.ApiService;
import com.onlineshop.onlineshop.Exceptions.CustomExceptions.AuthenticationFailureException;
import com.onlineshop.onlineshop.JwtUtil;
import com.onlineshop.onlineshop.Models.DTO.*;
import com.onlineshop.onlineshop.Models.DTO.User.SignUpDTO;
import com.onlineshop.onlineshop.Models.EverythingElse.TwoFactorCodeDTO;
import com.onlineshop.onlineshop.Models.vk.ApiResponse;
import com.onlineshop.onlineshop.Services.AuthService;
import com.onlineshop.onlineshop.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ApiService apiService;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);


    @PostMapping("/verifyTwoFactorCode")
    public ResponseEntity<ApiResponse> verifyTwoFactorCode(@RequestBody TwoFactorCodeDTO twoFactorCodeDTO) {
        return ResponseEntity.ok(authService.validateAndGenerateJwt(twoFactorCodeDTO));
    }

    @PostMapping("/exchangeSilentAuthToken")
    public CompletableFuture<ResponseEntity<ApiResponse>> exchangeSilentAuthToken(@RequestBody SilentAuthDTO silentAuthDTO) {
        return authService.exchangeAndRetrieveProfile(silentAuthDTO.getSilentToken(), silentAuthDTO.getUuid())
                .thenApply(body -> ResponseEntity.ok().body(body))
                .exceptionally(e -> {
                    Throwable cause = e.getCause();
                    if (cause instanceof AuthenticationFailureException) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,e.getMessage()){});
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,e.getMessage()){});
                });
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody SignUpDTO signUpDTO) {
        userService.registerUser(signUpDTO);
        return ResponseEntity.ok(new ApiResponse(true,"Регистрация прошла успешно!"){});
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ApiResponse> changePassword() {
        return ResponseEntity.ok(authService.resetPassword());
    }

    @PostMapping("/authenticate")
    public CompletableFuture<ResponseEntity<ApiResponse>> authenticate(@RequestBody AuthRequest request) {
        return authService.authenticateUser(request)
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> {
                    if (e.getCause() instanceof AuthenticationFailureException) {
                        logger.info("Username from request: {}",
                                request.getUsername());
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, e.getMessage()){});
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()){});
                    }
                });
    }

}

