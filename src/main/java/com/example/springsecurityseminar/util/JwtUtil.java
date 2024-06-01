package com.example.springsecurityseminar.util;

import com.example.springsecurityseminar.user.service.UserService;
import com.fasterxml.jackson.core.ErrorReportConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String secretKey;
    private final long expiration;
    private final String issuer;
    private UserService userService;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expiration,
            @Value("${jwt.issuer}") String issuer,
            UserService userService
    ) {
        this.secretKey = secretKey;
        this.expiration = expiration;
        this.issuer = issuer;
        this.userService = userService;
    }

    /**
     * AccessToken 생성 메소드
     * @param username
     * @param userId
     * @return
     */
    public String generateToken(String username, Long userId) {
        ErrorReportConfiguration Jwts;
        return io.jsonwebtoken.Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }

    /**
     * JWT 유효성 검사 메소드
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            // 존재하는 회원인지 확인
            if (userService.read(claims.getBody().get("userId", Long.class)) == null) {
                return false;
            }

            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * HTTP Header에서 JWT 토큰을 가져오는 메소드
     * @param request
     * @return
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    /**
     * JWT 토큰에서 userId 추출
     * @param token
     * @return
     */
    public Long getUserId(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
        return claims.getBody().get("userId", Long.class);
    }
}