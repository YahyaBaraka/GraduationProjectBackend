package com.example.graduationproject.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("Received error exception of type HandlerMethodValidationException");
        log.error("Error message is" + methodArgumentNotValidException.getMessage());
        log.error("Error Cause is" + methodArgumentNotValidException.getCause());

        List<String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        Map<String,Object> body = Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "errors", errors
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> handleMethodValidationException(HandlerMethodValidationException methodValidationException) {
        log.error("Received error exception of type HandlerMethodValidationException");
        log.error("Error message is" + methodValidationException.getMessage());
        log.error("Error Cause is" + methodValidationException.getCause());

        List<String> errors = methodValidationException.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError fe) {
                        return fe.getField() + ": " + fe.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .toList();
        Map<String, Object> body = Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "errors", errors
        );
        return ResponseEntity.badRequest().body(body);
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound(ProductNotFoundException productNotFoundException) {
        log.error("Received error exception of type ProductNotFoundException");
        log.error("Error message is" + productNotFoundException.getMessage());
        log.error("Error Cause is" + productNotFoundException.getCause());

        Map<String, String> body = new HashMap<>();
        body.put("error", productNotFoundException.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductConflictException.class)
    public ResponseEntity<Map<String, String>> handleProductConflict(ProductConflictException productConflictException) {
        log.error("Received error exception of type ProductConflictException");
        log.error("Error message is" + productConflictException.getMessage());
        log.error("Error Cause is" + productConflictException.getCause());

        Map<String, String> body = new HashMap<>();
        body.put("error", productConflictException.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ServiceProviderConflictException.class)
    public ResponseEntity<Map<String, String>> handleServiceProviderConflict(ServiceProviderConflictException serviceProviderConflictException) {
        log.error("Received error exception of type ServiceProviderConflictException");
        log.error("Error message is" + serviceProviderConflictException.getMessage());
        log.error("Error Cause is" + serviceProviderConflictException.getCause());

        Map<String, String> body = new HashMap<>();
        body.put("error", serviceProviderConflictException.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception exception) {
        log.error("Received error exception of type Exception");
        log.error("Error message is" + exception.getMessage());
        log.error("Error Cause is" + exception.getCause());

        Map<String, String> body = new HashMap<>();
        body.put("error", exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}