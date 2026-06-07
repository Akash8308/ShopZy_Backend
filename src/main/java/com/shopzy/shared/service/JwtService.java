package com.shopzy.shared.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    //TODO

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
