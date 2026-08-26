package com.monconcours.backend.service;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {
    private final String SECRET_KEY =
            "MonConcoursCleSecreteJWT2026TresLongueEtSecurisee123456";
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }
    public String genererToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey())
                .compact();
    }
    public String extraireEmail(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }
}
