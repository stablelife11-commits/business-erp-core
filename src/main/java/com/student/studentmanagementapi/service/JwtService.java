package com.student.studentmanagementapi.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "TXlTdXBlclNlY3JldEtleUZvclNwcmluZ0Jvb3RKV1QyMDI2QXV0aA==";

    // 👇 SECRET_KEY के नीचे
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    // 👇 getSignInKey() के नीचे
    public String generateToken(String mobile) {

        return Jwts.builder()
                .subject(mobile)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractMobile(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token, String mobile) {

        final String tokenMobile = extractMobile(token);

        return tokenMobile.equals(mobile) && !isTokenExpired(token);
    }
}