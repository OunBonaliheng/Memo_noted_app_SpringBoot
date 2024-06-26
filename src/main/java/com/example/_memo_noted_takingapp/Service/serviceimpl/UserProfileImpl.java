package com.example._memo_noted_takingapp.Service.serviceimpl;

import com.example._memo_noted_takingapp.Exception.NotFoundException;
import com.example._memo_noted_takingapp.Jwt.JwtService;
import com.example._memo_noted_takingapp.Model.User;
import com.example._memo_noted_takingapp.Model.dto.Response.UserResponse;
import com.example._memo_noted_takingapp.Repositority.OtpRepository;
import com.example._memo_noted_takingapp.Repositority.UserProfileRepository;
import com.example._memo_noted_takingapp.Repositority.UserRepository;
import com.example._memo_noted_takingapp.Service.AuthService;
import com.example._memo_noted_takingapp.Service.UserProfile;
import com.example._memo_noted_takingapp.config.PasswordConfig;
import com.example._memo_noted_takingapp.util.EmailUtil;
import com.example._memo_noted_takingapp.util.OtpUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProfileImpl implements UserProfile {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    public UserResponse changeUsername(String changeUsername) {
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        UserResponse user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        userProfileRepository.updateUsername(userId, changeUsername);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse getUserDetails() {
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        UserResponse user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        userProfileRepository.getUserDetails(userId);
        return modelMapper.map(user, UserResponse.class);
    }
}

