package com.example.springsecurityseminar.auth.service;

import com.example.springsecurityseminar.auth.dto.SignInReqDto;
import com.example.springsecurityseminar.auth.dto.SignUpReqDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecurityseminar.auth.dto.SignInResDto;
import com.example.springsecurityseminar.auth.entity.User;
import com.example.springsecurityseminar.util.JwtUtil;


@Service
public class AuthService {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder){
        this.userService=userService;
        this.jwtUtil=jwtUtil;
        this.passwordEncoder=passwordEncoder;
    }
    public SignInResDto signIn(SignInReqDto dto) {
        User user = userService.read(dto.getUsername());
        if (user.getPassword().equals(dto.getPassword())) {
            return new SignInResDto(jwtUtil.generateToken(user.getUsername(), user.getId()));
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public User signUp (SignUpReqDto dto){
        String username=dto.getUsername();
        String name=dto.getName();
        String password=passwordEncoder.encode(dto.getPassword());
        String phone=dto.getPhone();

        User user = User.builder()
                .username(username)
                .name(name)
                .password(password)
                .phone(phone)
                .build();

        return user;
    }
}