package com.example.springbootbackend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VerificationTokenUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Store securely
    private static final long EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes in ms

    public static String generateVerificationToken(String email, String code) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("code", code);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject("verification-code")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token, String inputEmail, String inputCode) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.get("email", String.class);
            String code = claims.get("code", String.class);

            return email.equals(inputEmail) && code.equals(inputCode);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token); // Will throw if expired or invalid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
