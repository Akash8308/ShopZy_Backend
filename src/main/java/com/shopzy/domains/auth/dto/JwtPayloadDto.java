package com.shopzy.domains.auth.dto;

import lombok.Data;

import java.util.Date;

@Data
public class JwtPayloadDto {

    private String sub;
    private String iss;
    private Date iat;
    private Date exp;
    private String jti;
    private String roles;
}