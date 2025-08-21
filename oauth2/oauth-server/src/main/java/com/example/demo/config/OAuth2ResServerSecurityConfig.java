package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuth2ResServerSecurityConfig {

    @Bean
    @Order
    SecurityFilterChain oauthResourceSever(HttpSecurity http,
                                           JwtDecoder jwtDecoder)
        throws Exception {
        // @formatter:off
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/hello").hasAuthority("SCOPE_api.read")
                .anyRequest().permitAll()
            )
            .oauth2ResourceServer((oauth2) -> oauth2
                .jwt(jwt -> jwt.decoder(jwtDecoder))
            );
        // @formatter:on
        return http.build();
    }
}
