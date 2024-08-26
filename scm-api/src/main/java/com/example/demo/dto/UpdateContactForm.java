package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContactForm {

    String id;
    
    @NotBlank(message = "Le nom est obligatoire !!!")
    @Size(min = 3, message = "Le nom doit contenir au minimum 3 caractères !!!")
    private String name;

    @NotBlank(message = "Email est obligatoire !!!")
    @Email(message = "Email incorrect !!!", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    @NotBlank(message = "Le numéro de téléphone est obligatoire !!!")
    @Pattern(regexp = "^[0-9]{10}$", message = "Numéro de téléphone incorrect !!!")
    private String phoneNumber;

    private String address;

    private MultipartFile contactImage;
    
    private String description;

    private boolean favorite;

    private String websiteLink;

    private String linkedInLink;

    private String picture;

}
