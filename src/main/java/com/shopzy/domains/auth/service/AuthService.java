package com.shopzy.domains.auth.service;

import com.shopzy.domains.auth.Repository.RefreshTokenRepository;
import com.shopzy.domains.auth.dto.AuthResponse;
import com.shopzy.domains.auth.dto.RegisterRequest;
import com.shopzy.domains.auth.dto.RegisterResponse;
import com.shopzy.domains.auth.dto.RegisterUserResponse;
import com.shopzy.domains.auth.model.RefreshToken;
import com.shopzy.domains.user.model.Users;
import com.shopzy.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}