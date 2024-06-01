package com.example.springsecurityseminar.user.service;

import com.example.springsecurityseminar.user.dto.SignInReqDto;
import com.example.springsecurityseminar.user.service.UserService;
import org.springframework.stereotype.Service;

import com.example.springsecurityseminar.user.dto.SignInReqDto;
import com.example.springsecurityseminar.user.dto.SignInResDto;
import com.example.springsecurityseminar.user.entity.User;
import com.example.springsecurityseminar.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public SignInResDto signIn(SignInReqDto dto) {
        User user = userService.read(dto.getUsername());
        if (user.getPassword().equals(dto.getPassword())) {
            return new SignInResDto(jwtUtil.generateToken(user.getUsername(), user.getId()));
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }
}