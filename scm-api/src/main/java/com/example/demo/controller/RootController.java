package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import com.example.demo.validators.GetEmailAuth;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void AddLoggedUserInfo(Model model, Authentication authentication, Principal principal) {
       
        if (authentication== null){
            return;
        }

        String username = GetEmailAuth.getEmailForAuth(authentication);

        User loggedInUser = this.userService.getUserByEmail(username);
        if (loggedInUser == null) {
            model.addAttribute("loggedInUser", null);
        } else {
            model.addAttribute("loggedInUser", loggedInUser);
        }
    }

}
