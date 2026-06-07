package com.shopzy.shared.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        Long userId,
        String email,
        String role
) {
}
