package com.shopzy.domains.auth.service;

import com.shopzy.domains.user.model.Users;
import com.shopzy.domains.user.repository.UserRepository;
import com.shopzy.shared.valueobject.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;

@Slf4j
@Service
public class JwtService extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateAccessToken(Users user) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .subject(user.getEmail())
                .issuer("shpzy-api")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .id(UUID.randomUUID().toString())
                .claim("roles", Role.USER.name())
                .signWith(getSecreteKey())
                .compact();
    }

    public String generateRefreshToken() {
        byte[] randomBytes = new byte[64];

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    private Key getSecreteKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    public String extractEmail(String token) throws JsonProcessingException {
//        String[] chunks = token.split("\\.");
//
//        Base64.Decoder decoder = Base64.getDecoder();
//
//        String header = new String(decoder.decode(chunks[0]));
//        String claims = new String(decoder.decode(chunks[1]));
//
//        JwtPayloadDto payload = objectMapper.readValue(claims, JwtPayloadDto.class);
//        return payload.getSub();
//    }

    public boolean validateToken(String token, UserDetails userDetails) {

        try {
            Claims claims = extractAllClaims(token);

            String email = claims.getSubject();

            return email != null
                    && email.equals(userDetails.getUsername())
                    && !isTokenExpired(claims);

        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(String token) {
        return false;
    }
}
