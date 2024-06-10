package com.onlineshop.onlineshop.Exceptions.CustomExceptions;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message) {
        super(message);
    }
}

