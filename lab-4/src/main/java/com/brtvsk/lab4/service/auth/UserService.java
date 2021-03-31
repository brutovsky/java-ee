package com.brtvsk.lab4.service.auth;

import com.brtvsk.lab4.config.auth.MyPasswordEncoder;
import com.brtvsk.lab4.model.auth.UserEntity;
import com.brtvsk.lab4.model.auth.UserRegistrationForm;
import com.brtvsk.lab4.model.auth.UserRoleEntity;
import com.brtvsk.lab4.model.auth.type.Role;
import com.brtvsk.lab4.repository.auth.UserRepository;
import com.brtvsk.lab4.repository.auth.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final MyPasswordEncoder passwordEncoder;

    public UserEntity registerNewUserAccount(UserRegistrationForm user) {
        Optional<UserEntity> userFromDB = userRepository.findByLogin(user.getLogin());

        if (userFromDB.isPresent()) {
            throw new UserAlreadyExistsException(
                    "Вже існує акаунт з таким логіном:  " + user.getLogin());
        }
        UserEntity userToSave = UserEntity.builder().login(user.getLogin()).build();
        userToSave.setUserRole(userRoleRepository.getUserRoleEntityByRole(Role.DEFAULT));
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(userToSave);
    }

}
