package com.onlineshop.onlineshop.Exceptions.CustomExceptions;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}

