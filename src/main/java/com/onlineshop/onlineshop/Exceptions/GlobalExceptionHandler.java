package com.onlineshop.onlineshop.Exceptions;

import com.onlineshop.onlineshop.ApiResponse1;
import com.onlineshop.onlineshop.Exceptions.CustomExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationFailureException.class)
    public ResponseEntity<ApiResponse1<Void>> handleAuthenticationFailureException(AuthenticationFailureException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse1.withMessage(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse1<Void>> handleDuplicateResourceException(DuplicateResourceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse1.withMessage(ex.getMessage()));
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse1<Void>> handleInvalidRequestException(InvalidRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse1.withMessage(ex.getMessage()));
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ApiResponse1<Void>> handleOutOfStockException(OutOfStockException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse1.withMessage(ex.getMessage()));
    }

    @ExceptionHandler(PaymentFailureException.class)
    public ResponseEntity<ApiResponse1<Void>> handlePaymentFailureException(PaymentFailureException ex) {
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(ApiResponse1.withMessage(ex.getMessage()));
    }

    @ExceptionHandler(RegistrationFailureException.class)
    public ResponseEntity<ApiResponse1<Void>> handleRegistrationFailureException(RegistrationFailureException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse1.withMessage(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse1<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse1.withMessage(ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse1<Void>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse1.withMessage(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse1<Void>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse1.withMessage("An unexpected error occurred"));
    }
}

