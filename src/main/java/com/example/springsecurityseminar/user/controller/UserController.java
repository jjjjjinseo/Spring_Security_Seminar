package com.example.springsecurityseminar.user.controller;

import com.example.springsecurityseminar.user.service.UserService;
import com.example.springsecurityseminar.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    private final JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public ResponseEntity<?> read(HttpServletRequest request, @PathVariable Long id) {
//        Long userId = jwtUtil.getUserId(jwtUtil.resolveToken(request).substring(7));
//        if (!userId.equals(id)) {
//            return ResponseEntity.badRequest().build();
//        }
        return ResponseEntity.ok(userService.read(id));
    }
}