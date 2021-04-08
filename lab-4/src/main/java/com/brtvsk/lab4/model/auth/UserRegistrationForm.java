package com.brtvsk.lab4.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationForm {
    @Length(min = 3, max = 20, message = "Login length must be from 3 to 20")
    @Pattern(regexp = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{0,19}$", message = "Login must contain only latin letters and numbers and start with letter")
    private String login;
    @Length(min = 8, max = 20, message = "Password length must be from 8 to 20")
    private String password;
    @NotBlank(message = "Repeat password")
    private String passwordConfirm;
}
