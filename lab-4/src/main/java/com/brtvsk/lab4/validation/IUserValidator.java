package com.brtvsk.lab4.validation;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.auth.UserRegistrationForm;

import java.util.List;

public interface IUserValidator {
    List<String> validateUserRegistrationForm(final UserRegistrationForm bookDto);
    List<String> validatePassword(final UserRegistrationForm bookDto);
}
