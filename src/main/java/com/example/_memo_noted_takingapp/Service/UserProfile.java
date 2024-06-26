package com.example._memo_noted_takingapp.Service;

import com.example._memo_noted_takingapp.Model.dto.Response.UserResponse;

public interface UserProfile {
    UserResponse changeUsername(String changeUsername);

    UserResponse getUserDetails();
}
