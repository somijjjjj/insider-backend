package com.firstproject.insider.system.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.expirationTime}")
    private int  EXPIRATION_TIME; // 1일 (밀리초)

    public String generateToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] bytes = Base64.getDecoder()
                .decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(bytes, "HmacSHA256");
    }


    /*public String extractUserId(String token) {
        Claims claims = Jwts.builder()
                .signWith(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();  // 토큰에서 subject (userId)를 추출
    }*/


    /*public boolean validateToken(String token) {
        try {
            Jwts.builder()
                    .signWith(getSignInKey())  // 서명 검증을 위한 키 설정
                    .build()
                    .parseClaimsJws(token);  // 토큰 파싱 및 서명 검증
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰이 유효하지 않은 경우 처리
            return false;
        }
    }*/
}
