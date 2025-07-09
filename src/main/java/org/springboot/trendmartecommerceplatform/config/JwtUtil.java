package org.springboot.trendmartecommerceplatform.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {

    private final String secret = "1234509876daryce1234509876ineza1234509876Mwambutsa1234509876daryce123459876mwambutsa1234509876ineza1234509876";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String extractUserName(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    public String extractUsername(String token) {
        return extractUserName(token);
    }

    public Boolean isTokenValid(String jwtToken) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
