package com.example.springsecurityseminar.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInReqDto {
    private String username;
    private String password;
}