package com.example.demo.controller;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.UserProfileForm;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import com.example.demo.validators.GetEmailAuth;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/dashboard")
    public String userDashboard(Authentication authentication, Principal principal, Model model) {
        logger.info("Get /dashboard => dashboard utilisateur demandé...");
        return "user/user_dashboard";
    }

    @GetMapping("/profile")
    public String userProfileGet(Authentication authentication, Model model) {
        String username = GetEmailAuth.getEmailForAuth(authentication);
        User userProfile = this.userService.getUserByEmail(username);

        UserProfileForm userProfileForm = new UserProfileForm();

        userProfileForm.setUserPic(userProfile.getUserImage());
        userProfileForm.setEmail(userProfile.getEmail());
        userProfileForm.setPseudo(userProfile.getPseudo());
        userProfileForm.setPassword("");
        userProfileForm.setDescription(userProfile.getDescription());

        model.addAttribute("userProfileForm", userProfileForm);
        model.addAttribute("userId", userProfile.getUserId());
        
        logger.info("Get /userProfileGet => profil utilisateur demandé " + userProfileForm.getEmail());
        return "user/user_profile";
    }

    @PostMapping("/profile/{userId}")
    public String userProfilePost(
            @Valid @ModelAttribute UserProfileForm userProfileForm,
            BindingResult bindingResult,
            Model model,
            @PathVariable("userId") String userId,
            @RequestParam("password") String password
           ) {

        if (bindingResult.hasErrors()) {
            return "user/user_profile";
        }

        if (password.trim().length() > 0) {
            if (password.trim().length() < 6) {
                model.addAttribute("errorPassword", "errorPassword");
                return "user/user_profile";
            }
        }

        this.userService.updateUser(userProfileForm, userId, password);
        logger.info("Post /userProfilePost => profil utilisateur màj " + userProfileForm.getEmail());
        return "user/user_dashboard";
    }
}
