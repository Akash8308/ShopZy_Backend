package com.shopzy.domains.auth.securityConfig;

import com.shopzy.domains.auth.dto.UserDto;
import com.shopzy.domains.user.model.Users;
import com.shopzy.domains.user.repository.UserRepository;
import com.shopzy.domains.auth.service.JwtService;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tools.jackson.databind.ObjectMapper;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess (
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException {

        logger.info("Authentication Success");

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String code = UUID.randomUUID().toString();

        UserDto userDto = UserDto.builder()
                .email(oauthUser.getAttribute("email"))
                .name(oauthUser.getAttribute("name"))
                .uuid(code)
                .build();

        String json = objectMapper.writeValueAsString(userDto);

        redisTemplate.opsForValue().set(
                "oauth:" + code,
                json,
                Duration.ofMinutes(5)
        );

        logger.info( "Stored: " + redisTemplate.opsForValue().get("oauth:" + code));

        Users user = userRepository.findByEmail(oauthUser.getAttribute("email"))
                .orElseGet(() -> {
                    Users newUser = new Users();
                    newUser.setUsername(oauthUser.getAttribute("name"));
                    newUser.setName(oauthUser.getAttribute("given_name"));
                    newUser.setEmail(oauthUser.getAttribute("email"));
                    return userRepository.save(newUser);
                });

        logger.info("Redirecting to: " + frontendUrl + "/callback" + "/?code=" + code);
        response.sendRedirect(frontendUrl + "/callback" + "/?code=" + code);
    }
}
