package com.example._memo_noted_takingapp.Service;

import com.example._memo_noted_takingapp.Model.User;
import com.example._memo_noted_takingapp.Model.dto.Request.ForgetRequest;
import com.example._memo_noted_takingapp.Model.dto.Request.LoginRequest;
import com.example._memo_noted_takingapp.Model.dto.Request.RegisterRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.AuthResponse;
import com.example._memo_noted_takingapp.Model.dto.Response.UserResponse;
import jakarta.mail.MessagingException;


public interface UserService {
    UserResponse register(RegisterRequest registerRequest) throws MessagingException;

    AuthResponse login(LoginRequest loginRequest);

    boolean verifyOtp(String otpCode);

    String resendOtp(String email);

    UserResponse forgetPassword(ForgetRequest forgetRequest, String email);

    Long getUsernameOfCurrentUser();


}
