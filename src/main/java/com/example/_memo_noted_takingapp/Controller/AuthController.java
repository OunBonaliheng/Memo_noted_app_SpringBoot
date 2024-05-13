package com.example._memo_noted_takingapp.Controller;

import com.example._memo_noted_takingapp.Exception.InvalidInputException;
import com.example._memo_noted_takingapp.Exception.NotFoundException;
import com.example._memo_noted_takingapp.Model.Otp;
import com.example._memo_noted_takingapp.Model.User;
import com.example._memo_noted_takingapp.Model.dto.Request.ForgetRequest;
import com.example._memo_noted_takingapp.Model.dto.Request.LoginRequest;
import com.example._memo_noted_takingapp.Model.dto.Request.RegisterRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.APIResponse;
import com.example._memo_noted_takingapp.Model.dto.Response.AuthResponse;
import com.example._memo_noted_takingapp.Model.dto.Response.UserResponse;
import com.example._memo_noted_takingapp.Repositority.OtpRepository;
import com.example._memo_noted_takingapp.Repositority.UserRepository;
import com.example._memo_noted_takingapp.Service.AuthService;
import com.example._memo_noted_takingapp.Service.UserService;
import com.example._memo_noted_takingapp.config.PasswordConfig;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final  OtpRepository otpRepository;
    private final PasswordConfig passwordConfig;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    @PostMapping("/api/memo/notes/Auth/register")
    public ResponseEntity<APIResponse<UserResponse>> register(@RequestBody @Valid RegisterRequest registerRequest) throws MessagingException  {
        if (!isValidPassword(registerRequest.getPassword())) {
            throw new InvalidInputException("Password must be at least 8 characters long and contain at least one digit, one letter, and one special character.");
        }
        if (!registerRequest.getEmail().endsWith("@gmail.com")) {
            throw new InvalidInputException("Only Gmail addresses are allowed for registration.");
        }
        UserResponse userResponse = userService.register(registerRequest);

        System.out.println(userResponse);
        System.out.println(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>(
                "Please Check Email for Verify OTP Code", userResponse, HttpStatus.CREATED,new Date()
        ));
    }

    @PostMapping("/api/memo/notes/Auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        AuthResponse response = userService.login(loginRequest);
        System.out.println(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>(
                "Login Successful", response, HttpStatus.CREATED,new Date()
        ));
    }
    @PutMapping("/api/memo/notes/Auth/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam @Positive(message = "OTP Code must be positive number") String otpCode) {
        boolean response = userService.verifyOtp(otpCode);
        System.out.println(response);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(
                "Your Account is Verify Successfully", response, HttpStatus.OK,new Date()
        ));
    }
    @PostMapping("/api/memo/notes/Auth/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestParam @Valid String email) {
        String message = userService.resendOtp(email);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/api/memo/notes/Auth/forget-password")
    public ResponseEntity<UserResponse> forgetPassword(@RequestBody @Valid ForgetRequest forgetRequest, @RequestParam @Valid String email) {
        if (!isValidPassword(forgetRequest.getPassword())) throw new InvalidInputException("Password must be at least 8 characters long and contain at least one digit, one letter, and one special character.");
        UserResponse user  = userService.forgetPassword(forgetRequest, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @GetMapping("/api/memo/notes/Auth/findUserMatchPassword")
    public ResponseEntity<APIResponse<User>> findUserEmailPassword(@RequestParam String email, @RequestParam String password) {
        String passwordEncode = passwordEncoder.encode(password);
        User userEmail = userRepository.getUserByEmailandPassword(email,passwordEncode);
        if (userEmail == null) {
            throw new NotFoundException("This Email doesn't exist. You can Register with this Email");
        }
        // Compare the plain text password with the encoded password from the database
        if (!passwordEncoder.matches(password, userEmail.getPassword())) {
            throw new NotFoundException("Your password is Invalid, please try again.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(
                "Get User By Email", userEmail, HttpStatus.OK, new Date()
        ));
    }

    @GetMapping("/api/memo/notes/Auth/findUserEmail")
    public ResponseEntity<APIResponse<User>> findEmail(@RequestParam @Valid String email){
        User userEmail = userRepository.getUserByEmail(email);
        if (userEmail == null) throw new NotFoundException("This Email doesn't exist.You can Register with this Email");
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(
                "Get User by Email",userEmail,HttpStatus.OK,new Date()
        ));
    }


    @GetMapping("/api/memo/notes/Auth/findOTP")
    public ResponseEntity<APIResponse<Otp>> findOTP(@RequestParam @Valid String otp) {
        Otp message = otpRepository.getLatestOtpByCode(otp);
        if (message == null) throw new NotFoundException("This OTP doesn't exist."+ otp);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(
                "Get OTP By Code",message,HttpStatus.OK,new Date()
        ));
    }


    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }
}
