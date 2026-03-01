package com.manikanda.movie_ticket_booking_system.exception;

import java.util.HashMap;
import java.util.Map;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        error.put("status", HttpStatus.CONFLICT.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Map<String, String>> handlePSQLException(PSQLException e) {
        Map<String, String> error = new HashMap<>();
        String errorMessage = e.getMessage();
        
        if (errorMessage.contains("duplicate key value violates unique constraint")) {
            String constraintName = extractConstraintName(errorMessage);
            error.put("message", "A record with the same values already exists. Please ensure all fields are unique.");
            error.put("constraint", constraintName);
            error.put("status", HttpStatus.CONFLICT.toString());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        
        error.put("message", "Database error occurred. Please try again later.");
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private String extractConstraintName(String errorMessage) {
        int startIndex = errorMessage.indexOf("\"");
        int endIndex = errorMessage.lastIndexOf("\"");
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return errorMessage.substring(startIndex + 1, endIndex);
        }
        return "UNKNOWN";
    }

}
