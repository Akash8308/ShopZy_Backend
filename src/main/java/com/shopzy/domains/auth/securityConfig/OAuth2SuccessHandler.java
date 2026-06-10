package com.shopzy.domains.auth.securityConfig;

import com.shopzy.domains.user.model.Users;
import com.shopzy.domains.user.repository.UserRepository;
import com.shopzy.domains.auth.service.JwtService;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess (
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        Users user = userRepository.findByEmail(oauthUser.getAttribute("email"))
                .orElseGet(() -> {
                    Users newUser = new Users();
                    newUser.setUsername(oauthUser.getAttribute("username"));
                    newUser.setEmail(oauthUser.getAttribute("email"));
                    return userRepository.save(newUser);
                });

        String token = jwtService.generateAccessToken(user);

        response.sendRedirect(frontendUrl + "/dashboard" + "/?token=" + token);
    }
}
