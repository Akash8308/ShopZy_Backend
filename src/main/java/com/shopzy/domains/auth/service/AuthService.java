package com.shopzy.domains.auth.service;

import com.shopzy.domains.auth.dto.RegisterRequest;
import com.shopzy.domains.user.model.Users;
import com.shopzy.domains.auth.dto.AuthResponse;
import com.shopzy.domains.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse verify(Users user) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(),
                                user.getPassword()
                        )
                );

        if(authentication.isAuthenticated()) {

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken();

            return new AuthResponse(
                    accessToken,
                    refreshToken,
                    user.getId(),
                    user.getEmail(),
                    user.getRole().toString()
            );
        }

        throw new RuntimeException("Invalid credentials");
    }

    public void register(RegisterRequest request) {

        if (userService.existsByEmail(request.email())) {
            throw new RuntimeException("Email already registered");
        }

        Users user = new Users();

        user.setName(request.name());
        user.setEmail(request.email());

        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        userService.createUser(user);
    }
}

