package com.onlineshop.onlineshop.Exceptions.CustomExceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}