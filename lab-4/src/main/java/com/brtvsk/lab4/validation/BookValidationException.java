package com.brtvsk.lab4.validation;

public class BookValidationException extends RuntimeException {
    public BookValidationException(String errorMessage) {
        super(errorMessage);
    }
}
