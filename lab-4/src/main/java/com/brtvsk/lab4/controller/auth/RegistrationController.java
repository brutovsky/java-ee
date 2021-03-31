package com.brtvsk.lab4.controller.auth;

import com.brtvsk.lab4.model.auth.UserRegistrationForm;
import com.brtvsk.lab4.service.MyUserDetailsService;
import com.brtvsk.lab4.service.auth.IUserService;
import com.brtvsk.lab4.service.auth.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final IUserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        UserRegistrationForm userForm = new UserRegistrationForm();
        model.addAttribute("userForm", userForm);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") UserRegistrationForm userForm, Model model) {

        System.out.println("AAAAAAAAAAAAAA");

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Паролі не співпадають");
            return "registration";
        }
        try{
            userService.registerNewUserAccount(userForm);
        }catch (UserAlreadyExistsException uaee){
            model.addAttribute("loginError", uaee.getMessage());
            return "registration";
        }

        return "redirect:/login";
    }
}
