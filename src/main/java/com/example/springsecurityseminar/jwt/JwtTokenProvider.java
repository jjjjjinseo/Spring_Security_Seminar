package com.example.springsecurityseminar.token;

import com.example.springsecurityseminar.auth.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// JWT 토큰 생성/관리/인증 하는 클래스
@Component
public class JwtTokenProvider {
    private final String secretKey;
    private final long expiration;
    private final String issuer;
    private UserService userService;

    //application.yml, @Value import(lombok하면 안됨..)
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expiration,
            @Value("${jwt.issuer}") String issuer,
            UserService userService
    ){
        this.secretKey = secretKey;
        this.expiration = expiration;
        this.issuer = issuer;
    }
    public createAccessToken(String username, Long userId){
        Claims claims = Jwts.claims().setSubject(username);
        return 
    }

}
