package com.shopzy.shared.service;

import com.shopzy.domains.user.model.Users;
import com.shopzy.shared.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public AuthResponse verify(Users user) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(),
                                user.getPassword()
                        )
                );

        if(authentication.isAuthenticated()) {

            String accessToken = jwtService.generateToken(user);
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
}

