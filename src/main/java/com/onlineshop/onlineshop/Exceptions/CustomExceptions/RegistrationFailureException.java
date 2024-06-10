package com.onlineshop.onlineshop.Exceptions.CustomExceptions;

public class RegistrationFailureException extends RuntimeException {
    public RegistrationFailureException(String message) {
        super(message);
    }
}
