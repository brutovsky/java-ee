package com.brtvsk.lab4.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationForm {
    private String login;
    private String password;
    private String passwordConfirm;
}
