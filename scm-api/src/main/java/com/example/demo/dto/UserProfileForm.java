package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserProfileForm {

    @NotBlank(message = "Le pseudo est obligatoire !!!")
    @Size(min = 3, message = "Le pseudo doit contenir au minimum 3 caractères !!!")
    private String pseudo;

    private String email;

    private String password;

    private String description;

    private String userPic;
    
    private MultipartFile userImage;

}
