package com.onlineshop.onlineshop.Exceptions.CustomExceptions;

public class AuthenticationFailureException extends RuntimeException {
    public AuthenticationFailureException(String message) {
        super(message);
    }
}

