package kma.topic2.junit.validation;

import kma.topic2.junit.exceptions.ConstraintViolationException;
import kma.topic2.junit.exceptions.LoginExistsException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidator userValidator;

    @Test
    void shouldPasswordValidation() {
        final NewUser testUser = NewUser.builder()
                .fullName("Toad Brown")
                .login("toadlogin")
                .password("pass66")
                .build();

        userValidator.validateNewUser(testUser);

        Mockito.verify(userRepository).isLoginExists("toadlogin");
    }

    @Test
    void shouldThrowException_whenLoginExists() {
        Mockito.when(userRepository.isLoginExists("toadlogin")).thenReturn(true);

        Assertions.assertThatThrownBy(() -> userValidator.validateNewUser(
                NewUser.builder()
                        .fullName("Toad Brown")
                        .login("toadlogin")
                        .password("pass66")
                        .build()
                )
        ).isInstanceOf(LoginExistsException.class);
    }

    @ParameterizedTest
    @MethodSource("testPasswordDataProvider")
    void shouldThrowException_whenPasswordInvalid(String password, List<String> errors) {
        Assertions.assertThatThrownBy(() -> userValidator.validateNewUser(
                NewUser.builder()
                        .fullName("Toad Brown")
                        .login("toadlogin")
                        .password(password)
                        .build()
                )
        )
                .isInstanceOfSatisfying(
                        ConstraintViolationException.class,
                        ex -> Assertions.assertThat(ex.getErrors()).isEqualTo(errors)
                );
    }

    private static Stream<Arguments> testPasswordDataProvider() {
        return Stream.of(
                Arguments.of("p2", Arrays.asList("Password has invalid size")),
                Arguments.of("password9", Arrays.asList("Password has invalid size")),
                Arguments.of("ЄЇҐ", Arrays.asList("Password doesn't match regex")),
                Arguments.of("#######8", Arrays.asList("Password has invalid size", "Password doesn't match regex"))
        );
    }

}