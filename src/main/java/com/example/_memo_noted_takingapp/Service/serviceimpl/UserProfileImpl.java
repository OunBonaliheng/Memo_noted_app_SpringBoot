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
    public UserResponse changeUsername(String email, String changeUsername) {
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new NotFoundException("Email not found");
        }
        System.out.println(changeUsername);
        user.setName(changeUsername);
        User updatedUser = userProfileRepository.updateUser(email, user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }
}

