package com.example._memo_noted_takingapp.Controller;

import com.example._memo_noted_takingapp.Exception.InvalidInputException;
import com.example._memo_noted_takingapp.Exception.NotFoundException;
import com.example._memo_noted_takingapp.Model.User;
import com.example._memo_noted_takingapp.Model.dto.Request.ForgetRequest;
import com.example._memo_noted_takingapp.Model.dto.Request.LoginRequest;
import com.example._memo_noted_takingapp.Model.dto.Request.RegisterRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.APIResponse;
import com.example._memo_noted_takingapp.Model.dto.Response.AuthResponse;
import com.example._memo_noted_takingapp.Model.dto.Response.UserResponse;
import com.example._memo_noted_takingapp.Service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/memo/notes/Auth/")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<APIResponse<UserResponse>> register(@RequestBody @Valid RegisterRequest registerRequest) throws MessagingException  {
        if (!isValidPassword(registerRequest.getPassword())) throw new InvalidInputException("Password must be at least 8 characters long and contain at least one digit, one letter, and one special character.");
        UserResponse userResponse = userService.register(registerRequest);
        System.out.println(userResponse);
        System.out.println(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>(
                "Please Check Email for Verify OTP Code", userResponse, HttpStatus.CREATED,new Date()
        ));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        AuthResponse response = userService.login(loginRequest);
        System.out.println(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>(
                "Login Successful", response, HttpStatus.CREATED,new Date()
        ));
    }
    @PutMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam @Positive(message = "OTP Code must be positive number") String otpCode) {
        boolean response = userService.verifyOtp(otpCode);
        System.out.println(response);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(
                "Your Account is Verify Successfully", response, HttpStatus.OK,new Date()
        ));
    }
    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestParam @Valid String email) {
        String message = userService.resendOtp(email);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/forget-password")
    public ResponseEntity<UserResponse> forgetPassword(@RequestBody @Valid ForgetRequest forgetRequest, @RequestParam @Valid String email) {
        if (!isValidPassword(forgetRequest.getPassword())) throw new InvalidInputException("Password must be at least 8 characters long and contain at least one digit, one letter, and one special character.");
        UserResponse user  = userService.forgetPassword(forgetRequest, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        String currentUserEmail = userService.getUsernameOfCurrentUser();
        User userProfile = userService.getUserCurrentByEmail(currentUserEmail);
        if (userProfile != null) {
            return ResponseEntity.ok(userProfile);
        }
        return ResponseEntity.badRequest().body(new APIResponse<>(
                "You are not logged in", null, HttpStatus.BAD_REQUEST, new Date()
        ));
    }


    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }
}
