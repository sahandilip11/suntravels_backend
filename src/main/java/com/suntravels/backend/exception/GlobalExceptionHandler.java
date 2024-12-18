package com.suntravels.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler to manage exceptions throughout the application.
 * Provides custom error responses for validation and generic exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors triggered by DTO validation annotations.
     * Captures field-specific error messages and returns them in a structured response.
     *
     * @param ex the {@link MethodArgumentNotValidException} thrown when validation fails
     * @return a {@link ResponseEntity} containing a map of field-specific error messages
     *         with an HTTP status of 400 (Bad Request)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all generic exceptions not explicitly covered by other exception handlers.
     * Returns a response with the exception's message.
     *
     * @param ex the generic {@link Exception} thrown
     * @return a {@link ResponseEntity} containing the exception message
     *         with an HTTP status of 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
