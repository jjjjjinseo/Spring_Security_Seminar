package com.example.springsecurityseminar.auth.service;

import com.example.springsecurityseminar.auth.dto.SignInReqDto;
import com.example.springsecurityseminar.auth.dto.SignUpReqDto;
import com.example.springsecurityseminar.jwt.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecurityseminar.auth.dto.SignInResDto;
import com.example.springsecurityseminar.auth.entity.User;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder){
        this.userService=userService;
        this.jwtTokenProvider=jwtTokenProvider;
        this.passwordEncoder=passwordEncoder;
    }
    public SignInResDto signIn(SignInReqDto dto) {
        User user = userService.read(dto.getUsername());
        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return new SignInResDto(jwtTokenProvider.generateToken(user.getUsername(), user.getId()));
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public User signUp (SignUpReqDto dto){
        String username=dto.getUsername();
        String name=dto.getName();
        String password=passwordEncoder.encode(dto.getPassword());
        String phone=dto.getPhone();

        //중복 가입 확인
        if(userService.checkUsername(username).isPresent()){
            throw new IllegalArgumentException("이미 등록된 사용자입니다.");
        }
        User user = User.builder()
                .username(username)
                .name(name)
                .password(password)
                .phone(phone)
                .build();

        return user;
    }
}