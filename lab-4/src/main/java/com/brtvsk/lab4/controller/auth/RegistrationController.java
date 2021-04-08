package com.brtvsk.lab4.controller.auth;

import com.brtvsk.lab4.model.auth.UserRegistrationForm;
import com.brtvsk.lab4.service.MyUserDetailsService;
import com.brtvsk.lab4.service.auth.IUserService;
import com.brtvsk.lab4.service.auth.UserAlreadyExistsException;
import com.brtvsk.lab4.validation.IUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final IUserService userService;
    private final IUserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        UserRegistrationForm userForm = new UserRegistrationForm();
        model.addAttribute("userForm", userForm);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") UserRegistrationForm userForm, Model model) {
        List<String> errors = userValidator.validateUserRegistrationForm(userForm);
        if(!errors.isEmpty()){
            model.addAttribute("registrationFormErrors", errors.stream().sorted().collect(Collectors.toList()));
            return "registration";
        }
        errors = userValidator.validatePassword(userForm);
        if(!errors.isEmpty()){
            model.addAttribute("passwordErrors", errors.stream().sorted().collect(Collectors.toList()));
            return "registration";
        }
        try{
            userService.registerNewUserAccount(userForm);
        }catch (UserAlreadyExistsException uaee){
            model.addAttribute("loginErrors", uaee.getMessage());
            return "registration";
        }

        return "redirect:/login";
    }
}
