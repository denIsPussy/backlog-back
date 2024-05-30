package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.ApiService;
import com.onlineshop.onlineshop.JwtUtil;
import com.onlineshop.onlineshop.Models.DTO.User.SignUpDTO;
import com.onlineshop.onlineshop.Models.TwoFactorCodeDTO;
import com.onlineshop.onlineshop.Models.vk.vkProfileInfo;
import com.onlineshop.onlineshop.Models.vk.vkProfileInfoDTO;
import com.onlineshop.onlineshop.Services.AuthService;
import com.onlineshop.onlineshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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

    @PostMapping("/verifyTwoFactorCode")
    public ResponseEntity<?> verifyTwoFactorCode(@RequestBody TwoFactorCodeDTO twoFactorCodeDTO) {
        try {
            String jwt = authService.validateAndGenerateJwt(twoFactorCodeDTO);
            return ResponseEntity.ok(AuthResponse.withJwt(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthResponse.withError(e.getMessage()));
        }
    }

    @PostMapping("/exchangeSilentAuthToken")
    public Mono<ResponseEntity<vkProfileInfoDTO>> exchangeSilentAuthToken(@RequestParam String silentToken, @RequestParam int uuid) {
        return authService.exchangeAndRetrieveProfile(silentToken, uuid)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().body(null));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO) {
        String message = userService.registerUser(signUpDTO);
        return ResponseEntity.ok(AuthResponse.withMessage(message));
    }

//    @PostMapping("/registerVk")
//    public ResponseEntity<?> registerUserVk(@RequestBody SignUpDTO signUpDTO) {
//        AuthResponse response = authService.registerUserVk(signUpDTO);
//        return ResponseEntity.status(response.getJwt() == null ? HttpStatus.UNAUTHORIZED : HttpStatus.OK).body(response);
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        try {
            AuthResponse response = authService.authenticateUser(request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthResponse.withError(e.getMessage()));
        }
    }
}

