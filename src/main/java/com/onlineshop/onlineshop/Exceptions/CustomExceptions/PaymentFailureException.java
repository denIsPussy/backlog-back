package com.onlineshop.onlineshop.Exceptions.CustomExceptions;

public class PaymentFailureException extends RuntimeException {
    public PaymentFailureException(String message) {
        super(message);
    }
}

