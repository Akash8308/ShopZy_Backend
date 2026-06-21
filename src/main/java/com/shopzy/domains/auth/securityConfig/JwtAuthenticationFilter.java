package com.shopzy.domains.auth.securityConfig;


import com.shopzy.domains.auth.service.CustomUserDetailsService;
import com.shopzy.domains.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException{

        log.info("JWT Filter invoked for path: {}",
                httpServletRequest.getServletPath());

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String email = null;
        String token = null;

       if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);

            try {
                email = jwtService.extractEmail(token);
            } catch (JwtException e) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

        }

        if( email != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
            UserDetails userdetails = applicationContext.getBean(CustomUserDetailsService.class).loadUserByEmail(email);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userdetails, null, userdetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
