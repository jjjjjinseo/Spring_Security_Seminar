package com.example.springsecurityseminar.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInReqDto {
    private String username;
    private String password;
}