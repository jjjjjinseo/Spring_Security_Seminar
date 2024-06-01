package com.example.springsecurityseminar.auth.dto;

import com.example.springsecurityseminar.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
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
