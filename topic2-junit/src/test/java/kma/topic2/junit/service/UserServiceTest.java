package kma.topic2.junit.service;

import kma.topic2.junit.exceptions.UserNotFoundException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.model.User;
import kma.topic2.junit.validation.UserValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @SpyBean
    private UserValidator userValidator;

    // Integration test
    @Test
    void shouldCreateUser() {
        final NewUser testUser = NewUser.builder()
                .fullName("Toad Brown")
                .login("toadlogin")
                .password("pass66")
                .build();

        userService.createNewUser(testUser);

        Mockito.verify(userValidator).validateNewUser(testUser);

        assertThat(userService.getUserByLogin(testUser.getLogin()))
                .returns("Toad Brown", User::getFullName)
                .returns("toadlogin", User::getLogin)
                .returns("pass66", User::getPassword);

    }

    @Test
    void shouldThrowException_whenUserNotExists() {
        Assertions.assertThatThrownBy(() -> userService.getUserByLogin("nologin"))
                .isInstanceOf(UserNotFoundException.class);
    }

}