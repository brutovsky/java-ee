package com.brtvsk.lab4.service.auth;

import com.brtvsk.lab4.model.auth.UserEntity;
import com.brtvsk.lab4.model.auth.UserRegistrationForm;

public interface IUserService {
    UserEntity registerNewUserAccount(UserRegistrationForm userForm) throws UserAlreadyExistsException;
}
