package com.example.demo.services;

import com.example.demo.dto.RegisterForm;
import com.example.demo.dto.UserProfileForm;
import com.example.demo.entities.User;

public interface UserService {
    User saveUser(RegisterForm registerForm);
    User updateUser(UserProfileForm userProfileForm,String userId, String password);

    User getUserById(String userId);
    
    User getUserByEmail(String email);
    User getUserByEmailToken(String token);

    boolean isUserExistByEmail(String email);

    
}
