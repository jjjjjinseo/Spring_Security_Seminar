package com.example.springsecurityseminar.jwt;

import com.example.springsecurityseminar.auth.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

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

    public String generateToken(String username, Long userId){
        Claims claims = Jwts.claims().setSubject(username);
        return Jwts.builder()
                .setClaims(claims)
                .claim("userId", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            // 존재하는 회원인지 확인
            if (userService.read(claims.getBody().get("userId", Long.class)) == null) {
                return false;
            }
            // 만료되었을 시 claims 객체가 안 만들어짐, false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            return claims.getBody().get("userId", Long.class);
        } catch (Exception e) {
            return null; // 토큰 파싱 실패 시 null 반환
        }
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

}
