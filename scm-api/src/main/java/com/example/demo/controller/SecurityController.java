package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.RegisterForm;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;

import jakarta.validation.Valid;

@Controller
public class SecurityController {
    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/register")
    public String getRegister(Model model) {
        RegisterForm registerForm = new RegisterForm();
        model.addAttribute("registerForm", registerForm);
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute RegisterForm registerForm, BindingResult bindingResult,
            Model model) {

        // valide registerForm
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // verifie si email existe dans la BDD
        boolean userExist = this.userService.isUserExistByEmail(registerForm.getEmail());

        if (!userExist) {
            // sauvegarde dans la BDD
            this.userService.saveUser(registerForm);
            logger.info("Post /register => " + registerForm.getEmail() + " => sauvegardé...");
            return "redirect:/register?inscriptionSuccess";
        } else {
            // message erreur email existe déjà
            model.addAttribute("emailExist", "emailExist");
            logger.info("Post /register => " + registerForm.getEmail() + " => utilisateur existe déjà...");
            return "register";
        }

    }

    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    @GetMapping("/auth/verify-email")
    public String verifyEmail(@RequestParam("token") String token) {

        User user = this.userService.getUserByEmailToken(token);

        if (user != null) {
            if (user.getEmailToken().equals(token)) {
                user.setEmailToken(null);
                user.setEmailVerified(true);
                user.setEnabled(true);
                
                this.userRepository.save(user);
                logger.info("Post /register/verify-email => " + user.getEmail() + " => email inscription token validé...");
                return "redirect:/login?tokenSuccess";
            }
        
            logger.info("Post /register/verify-email => " + user.getEmail() + " => email inscription token erreur...");
            return "redirect:/login?tokenError";
        }

        logger.info("Post /register/verify-email => email inscription token erreur...");
        return "redirect:/login?tokenError";
    }

}
