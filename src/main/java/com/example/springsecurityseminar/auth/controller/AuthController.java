package com.example.springsecurityseminar.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurityseminar.auth.dto.SignInReqDto;
import com.example.springsecurityseminar.auth.dto.SignUpReqDto;
import com.example.springsecurityseminar.auth.service.AuthService;
import com.example.springsecurityseminar.auth.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpReqDto dto) {
        userService.create(authService.signUp(dto));
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInReqDto dto) {
        return ResponseEntity.ok(authService.signIn(dto));
    }
}