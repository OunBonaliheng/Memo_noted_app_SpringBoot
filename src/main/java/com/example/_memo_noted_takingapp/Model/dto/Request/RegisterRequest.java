package com.example._memo_noted_takingapp.Model.dto.Request;

import com.example._memo_noted_takingapp.Model.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    @Email(message = "Invalid Email Input")
    private String email;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String confirmPassword;
//    @NotNull
//    private Gender gender;
//
//    private String profileImage;

}
