package com.onlineshop.onlineshop.Exceptions.CustomExceptions;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}

