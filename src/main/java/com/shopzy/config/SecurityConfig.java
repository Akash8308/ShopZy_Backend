package com.shopzy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;

public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable()) //temp
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("//api/auth/**").permitAll() //public endpoints
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); //temp

        return http.build();
    }
}
