package com.example.springsecurityseminar.jwt;

import com.example.springsecurityseminar.auth.service.UserService;

import com.fasterxml.jackson.core.ErrorReportConfiguration;
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
        this.userService=userService;
    }

    public String generateToken(String username, Long userId){
        Claims claims = Jwts.claims().setSubject(username);
        return io.jsonwebtoken.Jwts.builder()
                .setClaims(claims)
                .claim("userId", userId)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            // Bearer 검증
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            // 토큰을 파싱하여 claims 객체 생성
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);

            // 토큰에서 사용자 ID를 가져와 UserService에서 사용자를 조회
            Long userId = claims.getBody().get("userId", Long.class);
            if (userService.read(userId) == null) {
                System.out.println("유효하지 않은 사용자 ID: " + userId);
                return false; // 존재하지 않는 사용자 ID
            }

            // 토큰 만료 여부 확인
            if (claims.getBody().getExpiration().before(new Date())) {
                System.out.println("토큰이 만료되었습니다.");
                return false; // 토큰 만료
            }

            return true; // 유효한 토큰
        } catch (Exception e) {
            System.out.println("토큰 유효성 검사 중 예외 발생: " + e.getMessage());
            return false; // 유효성 검사 실패
        }
    }

    public Long getUserId(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            return claims.getBody().get("userId", Long.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

}
