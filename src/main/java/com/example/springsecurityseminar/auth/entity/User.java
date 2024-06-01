package com.example.springsecurityseminar.auth.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column
    private String phone;

    @Builder
    public User(String username, String name, String password, String phone) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }
    public void passwordEncode(PasswordEncoder passwordEncoder){
        this.password=passwordEncoder.encode(this.password);
    }
}
