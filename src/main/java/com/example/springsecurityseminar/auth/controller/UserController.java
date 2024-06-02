package com.example.springsecurityseminar.auth.controller;

import com.example.springsecurityseminar.auth.service.UserService;
import com.example.springsecurityseminar.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{id}")
    public ResponseEntity<?> read(HttpServletRequest request, @PathVariable Long id) {
        Long userId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request).substring(7));

        if (!userId.equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.read(id));
    }
}