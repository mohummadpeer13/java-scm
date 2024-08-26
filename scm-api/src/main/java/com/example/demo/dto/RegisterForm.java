package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {
    @NotBlank(message = "Le pseudo est obligatoire !!!")
    @Size(min = 3, message = "Le pseudo doit contenir au minimum 3 caractères !!!")
    private String pseudo;

    @NotBlank(message = "Email est obligatoire !!!")
    @Email(message = "Email incorrect !!!", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire !!!")
    @Size(min = 6, message = "Le mot de passe doit contenir au minimum 6 caractères !!!")
    private String password;
    
    private String description;
}
