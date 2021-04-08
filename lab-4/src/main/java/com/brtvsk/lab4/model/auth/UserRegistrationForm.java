package com.brtvsk.lab4.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationForm {
    @Length(min = 1, max = 20, message = "Login length must be from 1 to 20")
    @Pattern(regexp = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{0,19}$", message = "Login must contain only latin letters and numbers")
    private String login;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "Repeat password")
    private String passwordConfirm;
}
