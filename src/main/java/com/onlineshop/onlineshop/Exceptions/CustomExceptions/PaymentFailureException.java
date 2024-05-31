package com.onlineshop.onlineshop.Exceptions.CustomExceptions;

public class PaymentFailureException extends Exception {
    public PaymentFailureException(String message) {
        super(message);
    }
}

