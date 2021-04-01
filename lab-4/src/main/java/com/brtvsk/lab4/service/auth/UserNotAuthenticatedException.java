package com.brtvsk.lab4.service.auth;

public class UserNotAuthenticatedException extends RuntimeException {
    public UserNotAuthenticatedException(String errorMessage) {
        super(errorMessage);
    }
}
