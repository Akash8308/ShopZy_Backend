package com.shopzy.domains.auth.service;

import com.shopzy.domains.auth.Repository.RefreshTokenRepository;
import com.shopzy.domains.auth.dto.*;
import com.shopzy.domains.auth.model.RefreshToken;
import com.shopzy.domains.user.model.Users;
import com.shopzy.domains.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    public AuthResponse verify(Users loginRequest) {

        Authentication authentication = authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        Users authenticatedUser =
                (Users) authentication.getPrincipal();

        return buildAuthResponse(authenticatedUser);
    }

    public RegisterResponse register(RegisterRequest request) {

        if (userService.existsByEmail(request.email())) {
            throw new RuntimeException("Email already registered");
        }

        Users user = new Users(
                request.username(),
                request.email().toLowerCase()
        );

        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        Users savedUser = userService.createUser(user);

        if (savedUser == null) {
            throw new RuntimeException("Failed to register user");
        }

        log.info("User registered successfully: {}", savedUser.getEmail());

        AuthResponse authResponse = buildAuthResponse(savedUser);

        RegisterUserResponse registerUserResponse =
                new RegisterUserResponse(
                        savedUser.getId(),
                        savedUser.getUsername(),
                        savedUser.getEmail(),
                        savedUser.getRole().toString()
                );

        return new RegisterResponse(
                authResponse.refreshToken(),
                authResponse.accessToken(),
                registerUserResponse
        );
    }

    private Authentication authenticate(String email, String password) {

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email.toLowerCase(),
                        password
                )
        );
    }

    private AuthResponse buildAuthResponse(Users user) {

        String accessToken =
                jwtService.generateAccessToken(user);

        RefreshToken refreshToken =
                generateRefreshToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getRefreshToken(),
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole().toString()
        );
    }

    public RefreshToken generateRefreshToken(Users user) {

        String token = jwtService.generateRefreshToken();

        RefreshToken refreshToken = new RefreshToken(
                token,
                user,
                LocalDateTime.now().plusDays(7)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public AuthResponse exchange(String token) {
        if (isTokenValid(token)){
            Users user = extractUser(token);

            if (user != null) {
                return new AuthResponse(
                        jwtService.generateRefreshToken(),
                        jwtService.generateAccessToken(user),
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getRole().toString()

                );
            }
        }
        return null;
    }

    private Users extractUser(String token) {
        String email = extractEmail(token);

        if(email == null){
            return userService.getUserByEmail(email);
        }

        return null;
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}