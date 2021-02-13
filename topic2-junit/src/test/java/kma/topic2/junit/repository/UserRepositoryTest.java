package kma.topic2.junit.repository;

import kma.topic2.junit.exceptions.UserNotFoundException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void shouldSaveNewUser() {
        final NewUser newUser = NewUser.builder()
                .fullName("Viper Joan")
                .login("viperlogin")
                .password("66ssap")
                .build();

        final User expectedUser = User.builder()
                .fullName(newUser.getFullName())
                .login(newUser.getLogin())
                .password(newUser.getPassword())
                .build();

        Assertions.assertThat(userRepository.saveNewUser(newUser)).isEqualTo(expectedUser);
        Assertions.assertThat(userRepository.getUserByLogin(newUser.getLogin())).isEqualTo(expectedUser);
    }

    @Test
    void loginShouldExist() {
        Assertions.assertThat(userRepository.isLoginExists("viperlogin")).isTrue();
    }

    @Test
    void loginShouldNotExist() {
        Assertions.assertThat(userRepository.isLoginExists("nologin")).isFalse();
    }

    @Test
    void shouldThrowException_whenUserNotExists() {
        Assertions.assertThatThrownBy(() -> userRepository.getUserByLogin("nologin"))
                .isInstanceOf(UserNotFoundException.class);
    }

}