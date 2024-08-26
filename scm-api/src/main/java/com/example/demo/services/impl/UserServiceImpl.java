package com.example.demo.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.RegisterForm;
import com.example.demo.dto.UserProfileForm;
import com.example.demo.entities.Email;
import com.example.demo.entities.User;
import com.example.demo.helpers.ResourceNotFoundExeception;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.ImageService;
import com.example.demo.services.KafkaService;
import com.example.demo.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Value("${app.url}")
    private String APP_URL;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    ImageService imageService;

    @Override
    public User saveUser(RegisterForm registerForm) {
        User userSaved = new User();

        String userId = UUID.randomUUID().toString();
        String emailToken = UUID.randomUUID().toString();

        userSaved.setUserId(userId);
        userSaved.setPseudo(registerForm.getPseudo());
        userSaved.setEmail(registerForm.getEmail());
        userSaved.setEmailToken(emailToken);
        userSaved.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        userSaved.setDescription(registerForm.getDescription());
        userSaved.setUserImage("https://images2.imgbox.com/61/e5/y8C6ewrr_o.png");
        userSaved.setRoleList(List.of("ROLE_USER"));
        this.userRepository.save(userSaved);

        String emailLink = APP_URL + "auth/verify-email?token=" + userSaved.getEmailToken();
        String sendPseudo = userSaved.getPseudo();

        HashMap<String, Object> emailMap = new HashMap<>();
        emailMap.put("sendPseudo", sendPseudo);
        emailMap.put("emailLink", emailLink);

        Email email = new Email(userSaved.getEmail(), "Votre inscription sur SCM 2.0", "emails/email-signup", emailMap);
        this.kafkaService.sendSignUpEmail(email);

        return userSaved;
    }

    @Override
    public User updateUser(UserProfileForm userProfileForm, String userId, String password) {
        User userUpdate = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundExeception("User not find"));

        // traitement image à partir de multipartfile
        if (userProfileForm.getUserImage() != null && !userProfileForm.getUserImage().isEmpty()) {
            String[] imageData = this.imageService.uploadImage(userProfileForm.getUserImage());
            userUpdate.setUserImage(imageData[1]);
        }
        userUpdate.setPseudo(userProfileForm.getPseudo());
        userUpdate.setDescription(userProfileForm.getDescription());
        
        if (password.length() > 0) {
            userUpdate.setPassword(passwordEncoder.encode(password));
        }

        User userSave = this.userRepository.save(userUpdate);
        return userSave;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = this.userRepository.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User getUserByEmailToken(String token) {
        return this.userRepository.findByEmailToken(token).orElse(null);
    }

    @Override
    public User getUserById(String userId) {
        return this.userRepository.findById(userId).orElse(null);
    }
}
