package com.example.springsecurityseminar.auth.service;

import com.example.springsecurityseminar.auth.entity.User;
import com.example.springsecurityseminar.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void create(User user) {
        userRepository.save(user);
    }

    public User read(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User read(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Optional<User> checkUsername(String username){
       Optional<User>  users = userRepository.findByUsername(username);
       return users;
    }
}