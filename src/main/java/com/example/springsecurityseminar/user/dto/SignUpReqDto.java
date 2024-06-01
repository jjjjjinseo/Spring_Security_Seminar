package com.example.springsecurityseminar.user.dto;

import com.example.springsecurityseminar.user.entity.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SignUpReqDto {
    private String username;
    private String name;
    private String password;
    private String phone;

    public User toEntity() {
        return User.builder()
                .username(username)
                .name(name)
                .password(password)
                .phone(phone)
                .build();
    }
}
