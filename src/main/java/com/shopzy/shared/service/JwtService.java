package com.shopzy.shared.service;

import com.shopzy.domains.user.model.Users;
import com.shopzy.domains.user.repository.UserRepository;
import com.shopzy.shared.securityConfig.OAuth2SuccessHandler;
import com.shopzy.shared.valueobject.Role;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.*;

import io.jsonwebtoken.Jwts;

@Service
public class JwtService extends SimpleUrlAuthenticationSuccessHandler {

    @Value("$jwt.secret")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    public String generateToken(Users user) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .subject(user.getUsername())
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

    public String extractUsername(String username) {
        return "";
    }

    public boolean validateToken(String token,
                                 UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return false;
    }
}
