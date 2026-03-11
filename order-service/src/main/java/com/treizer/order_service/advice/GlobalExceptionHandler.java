package com.treizer.order_service.advice;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.treizer.order_service.advice.custom.BillingServiceException;
import com.treizer.order_service.advice.custom.OrderNotFoundException;
import com.treizer.order_service.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(
        OrderNotFoundException ex, 
        HttpServletRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
            Instant.now(),
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BillingServiceException.class)
    public ResponseEntity<ErrorResponse> handleBillingError(
        BillingServiceException ex,
        HttpServletRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
            Instant.now(),
            HttpStatus.SERVICE_UNAVAILABLE.value(),
            "Service Unavailable",
            ex.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
        IllegalArgumentException ex,
        HttpServletRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
            Instant.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            ex.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // ---------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericError(
        Exception ex,
        HttpServletRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
            Instant.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "Unexpected error occurred",
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
}
