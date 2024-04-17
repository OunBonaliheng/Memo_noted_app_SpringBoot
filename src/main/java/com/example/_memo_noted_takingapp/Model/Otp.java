package com.example._memo_noted_takingapp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Otp {
    private Long id;
    private Long userId;
    private String otpCode;
    private Timestamp issuedAt;
    private Timestamp expirationTime;
    private boolean verified;
}
