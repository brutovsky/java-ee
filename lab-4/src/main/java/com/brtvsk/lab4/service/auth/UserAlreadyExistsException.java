package com.brtvsk.lab4.service.auth;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
