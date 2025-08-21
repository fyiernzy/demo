package com.example.demo.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
public class OAuth2AuthServerSecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain oauthAuthorizationServer(HttpSecurity http,
                                                 JwtDecoder jwtDecoder,
                                                 // @formatter:off
                                                 /*
                                                  * These dependencies can be removed in fact.
                                                  * Showing them here just for clarity
                                                  */
                                                 // @formatter:on
                                                 JdbcRegisteredClientRepository jdbcRegisteredClientRepository,
                                                 OAuth2AuthorizationService oAuth2AuthorizationService,
                                                 OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService
    ) throws Exception {
        OAuth2AuthorizationServerConfigurer authServer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        RequestMatcher endpoints = authServer.getEndpointsMatcher();
        http
            .securityMatcher(endpoints)
            .with(
                authServer,
                server -> server
                    .registeredClientRepository(jdbcRegisteredClientRepository)
                    .authorizationService(oAuth2AuthorizationService)
                    .authorizationConsentService(oAuth2AuthorizationConsentService)
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers(endpoints))
            .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.decoder(jwtDecoder)));
        return http.build();
    }

    /*
     * For demo purpose, never use it at production level
     */
    @Bean
    InMemoryRegisteredClientRepository inMemoryRegisteredClientRepository() {
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("oauth-client-id")
            .clientSecret("{noop}oauth-client-secret")
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .scope("api.read")
            .build();
        return new InMemoryRegisteredClientRepository(client);
    }

    @Bean
    JdbcRegisteredClientRepository jdbcRegisteredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
                                                           JdbcRegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate,
                                                                         JdbcRegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
            .issuer("http://localhost:8081")
            .build();
    }

    @Bean
    JWKSource<SecurityContext> jwkSource() throws Exception {
        KeyPair kp = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
        RSAKey rsa = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
        return new ImmutableJWKSet<>(new JWKSet(rsa));
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
