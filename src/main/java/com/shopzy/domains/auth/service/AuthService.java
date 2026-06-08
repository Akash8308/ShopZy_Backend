package com.shopzy.domains.auth.service;

import com.shopzy.domains.auth.dto.RegisterRequest;
import com.shopzy.domains.user.model.Users;
import com.shopzy.domains.auth.dto.AuthResponse;
import com.shopzy.domains.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
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

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    user.getPassword()
                            )
                    );

            log.info("Authentication successful");

            if(authentication.isAuthenticated()) {

                log.info("Generating JWT for {}",
                        user.getEmail());

                Users authenticatedUser =
                        (Users) authentication.getPrincipal();

                String accessToken = jwtService.generateAccessToken(authenticatedUser);
                String refreshToken = jwtService.generateRefreshToken();

                return new AuthResponse(
                        accessToken,
                        refreshToken,
                        user.getId(),
                        user.getEmail(),
                        user.getRole().toString()
                );
            }
        } catch (Exception e) {

            log.error("Authentication failed", e);

            throw e;
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

