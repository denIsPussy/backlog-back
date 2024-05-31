package com.onlineshop.onlineshop.Exceptions.CustomExceptions;

public class AuthenticationFailureException extends Exception {
    public AuthenticationFailureException(String message) {
        super(message);
    }
}

