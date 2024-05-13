package com.example._memo_noted_takingapp.Model.dto.Response;

import com.example._memo_noted_takingapp.Model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private Long userId;
    private String username;
//    private String username;
    private String email;
//    private Gender gender;
//    private String profileImage;


}
