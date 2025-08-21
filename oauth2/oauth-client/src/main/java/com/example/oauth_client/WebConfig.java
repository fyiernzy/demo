package com.example.oauth_client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.SupplierClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .oauth2Client(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(JdbcTemplate jdbcTemplate) {
        return new SupplierClientRegistrationRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRepo) {
        InMemoryOAuth2AuthorizedClientService authorizedClientService = new InMemoryOAuth2AuthorizedClientService(
            clientRepo);
        AuthorizedClientServiceOAuth2AuthorizedClientManager manager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
            clientRepo, authorizedClientService);
        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder()
            .clientCredentials()
            .build();
        manager.setAuthorizedClientProvider(provider);
        return manager;
    }

    @Bean
    public WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        var oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
            authorizedClientManager);
        oauth2.setDefaultClientRegistrationId("service-b");
        return WebClient.builder()
            .filter(oauth2)
            .build();
    }

}
