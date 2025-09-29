package com.example.oauthserver.security.beans;

import com.example.oauthserver.security.utils.RequestMatcherUtils;
import com.example.oauthserver.security.properties.OauthBlocklistProperties;
import com.example.oauthserver.security.filter.FilterChainExceptionHandlerFilter;
import com.example.oauthserver.security.filter.RequestBlockingFilter;
import com.example.oauthserver.stereotype.SfcComponentBean;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
public class OAuthAuthServerSfcBeansConfig {

    public static final String PREFIX = "OAuthAuthServerSfcBeans.";

    public static final String REQUEST_BLOCKING_FILTER = PREFIX + "requestBlockingFilter";
    public static final String FILTER_CHAIN_EXCEPTION_HANDLER_FILTER = PREFIX
                                                                       + "filterChainExceptionHandlerFilter";

    public static final String OAUTH_BLOCKLIST_REQUEST_MATCHER = PREFIX
                                                                 + "oauthBlocklistRequestMatcher";

    @SfcComponentBean(OAUTH_BLOCKLIST_REQUEST_MATCHER)
    public RequestMatcher oauthBlocklistRequestMatcher(OauthBlocklistProperties oauthBlocklistProperties) {
        return RequestMatcherUtils.anyOf(oauthBlocklistProperties.path());
    }

    @SfcComponentBean(REQUEST_BLOCKING_FILTER)
    public RequestBlockingFilter requestBlockingFilter(@Qualifier(OAUTH_BLOCKLIST_REQUEST_MATCHER)
                                                       RequestMatcher requestMatcher) {
        return new RequestBlockingFilter(requestMatcher);
    }

    @SfcComponentBean(FILTER_CHAIN_EXCEPTION_HANDLER_FILTER)
    public FilterChainExceptionHandlerFilter filterChainExceptionHandlerFilter(
        @Qualifier("handlerExceptionResolver")
        HandlerExceptionResolver handlerExceptionResolver
    ) {
        return new FilterChainExceptionHandlerFilter(handlerExceptionResolver);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        // 1. Generate RSA key pair
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

        // 2. Generate RSAKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey
            .Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();

        // 3. Build the JwkSet
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }


}
