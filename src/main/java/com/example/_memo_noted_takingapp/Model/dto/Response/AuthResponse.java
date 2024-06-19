package com.example._memo_noted_takingapp.Model.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Long userId;
    private String username;
    private String email;
    private String accessToken;
}
