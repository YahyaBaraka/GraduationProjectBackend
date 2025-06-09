package com.example.graduationproject.exceptions;

public class ServiceProviderConflictException extends RuntimeException {
    public ServiceProviderConflictException(String message) {
        super(message);
    }
}