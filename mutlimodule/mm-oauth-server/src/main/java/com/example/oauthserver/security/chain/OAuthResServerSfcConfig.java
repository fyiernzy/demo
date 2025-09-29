package com.example.oauthserver.security.chain;

import com.example.oauthserver.stereotype.SfcBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuthResServerSfcConfig {

    @SfcBean
    public SecurityFilterChain serverResSfcConfig(HttpSecurity http) throws Exception {
        CommonSfcRules
            .applyCommonSettings(http)
            .securityMatcher("/client/**")
            .authorizeHttpRequests(registry -> registry
                .anyRequest().authenticated()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(Customizer.withDefaults())
            .oauth2ResourceServer(resource -> resource.jwt(Customizer.withDefaults()));
        TokenSettings.builder().build();
        return http.build();
    }
}
