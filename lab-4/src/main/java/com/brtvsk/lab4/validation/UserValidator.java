package com.brtvsk.lab4.validation;

import com.brtvsk.lab4.model.BookDto;
import com.brtvsk.lab4.model.auth.UserRegistrationForm;
import com.brtvsk.lab4.repository.BookRep;
import com.brtvsk.lab4.repository.auth.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserValidator implements IUserValidator{

    private final UserRepository userRepository;
    private final Validator validator;

    @Override
    public List<String> validateUserRegistrationForm(UserRegistrationForm userRegistrationForm) {
        final Set<ConstraintViolation<UserRegistrationForm>> validationResult = validator.validate(userRegistrationForm);
        final List<String> errors = validationResult
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return errors;
    }

    @Override
    public List<String> validatePassword(UserRegistrationForm bookDto) {
        final List<String> errors = new java.util.ArrayList<>(List.of());
        if(!bookDto.getPassword().equals(bookDto.getPasswordConfirm()))
            errors.add("Passwords do not match");
        return errors;
    }
}
