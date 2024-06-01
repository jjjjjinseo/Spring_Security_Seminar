package com.example.springsecurityseminar.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
