package com.example.springsecurityseminar.auth.dto;

import com.example.springsecurityseminar.auth.entity.User;
import lombok.Getter;

@Getter
public class SignUpResDto {
    private String username;
    private String name;
    private String password;
    private String phone;

    public SignUpResDto(User user){
        this.username=user.getUsername();
        this.name=user.getName();
        this.password=user.getPassword();
        this.phone=user.getPhone();
    }

}
