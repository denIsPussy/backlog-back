package com.onlineshop.onlineshop.Controllers;

public class AuthResponse {
    private String jwt = null;
    private String message = null;
    private String error = null;

    private AuthResponse(String jwt, String message, String error) {
        this.jwt = jwt;
        this.message = message;
        this.error = error;
    }

    public static AuthResponse withJwt(String jwt) {
        return new AuthResponse(jwt, null, null);
    }

    public static AuthResponse withMessage(String message) {
        return new AuthResponse(null, message, null);
    }

    public static AuthResponse withError(String error) {
        return new AuthResponse(null, null, error);
    }

    public String getJwt() {
        return jwt;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
