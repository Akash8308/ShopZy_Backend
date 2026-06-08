package com.shopzy.domains.auth.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String accessToken;

    public LoginResponse(String accessToken){
        this.accessToken = accessToken;
    }
}
