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
    private String name;
    private String email;


}
