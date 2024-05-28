package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.JwtUtil;
import com.onlineshop.onlineshop.Models.DTO.User.SignUpDTO;
import com.onlineshop.onlineshop.Models.TwoFactorCodeDTO;
import com.onlineshop.onlineshop.Models.User;
import com.onlineshop.onlineshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/verifyTwoFactorCode")
    public ResponseEntity<?> verifyTwoFactorCode(@RequestBody TwoFactorCodeDTO twoFactorCodeDTO) {
        User user = userService.getByUsername(twoFactorCodeDTO.getUsername());
        String code = twoFactorCodeDTO.getCode();
        if (userService.verify2FACode(code, user)) {
            final UserDetails userDetails = userService.loadUserByUsername(twoFactorCodeDTO.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt, null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse(null, "Invalid 2FA code"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO) {
        User newUser = new User();
        newUser.setEmail(signUpDTO.getEmail());
        newUser.setFirstName(signUpDTO.getFirstName());
        newUser.setLastName(signUpDTO.getLastName());
        newUser.setPatronymic(signUpDTO.getPatronymic());
        newUser.setUsername(signUpDTO.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(signUpDTO.getPassword()));
        userService.create(newUser);
        return ResponseEntity.ok(new AuthenticationResponse(null, "User registered successfully"));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            final User user = userService.getByUsername(request.getUsername());
            if (user.isTwoFactorEnabled()) {
                userService.generateAndSend2FACode(user.getUsername());
                return ResponseEntity.ok(new AuthenticationResponse(null, "2FA code sent to your email. Please verify to complete login."));
            }
            final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt, null));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse(null, "Authentication failed: " + e.getMessage()));
        }
    }
}

