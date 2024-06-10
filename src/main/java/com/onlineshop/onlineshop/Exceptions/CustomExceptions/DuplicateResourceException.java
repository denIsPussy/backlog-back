package com.onlineshop.onlineshop.Exceptions.CustomExceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
