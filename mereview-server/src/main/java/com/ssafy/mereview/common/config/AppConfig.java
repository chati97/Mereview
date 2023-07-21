package com.ssafy.mereview.common.config;

import com.ssafy.mereview.common.util.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

@Configuration
public class AppConfig {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.accessExpirationMs}")
    private int accessExpirationMs;

    @Value("${app.jwt.refreshExpirationMs}")
    private int refreshExpirationMs;

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils(jwtSecret, accessExpirationMs, refreshExpirationMs);
    }
}
