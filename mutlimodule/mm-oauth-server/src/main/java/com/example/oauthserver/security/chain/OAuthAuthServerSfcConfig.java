package com.example.oauthserver.security.chain;

import com.example.oauthserver.security.beans.OAuthAuthServerSfcBeansConfig;
import com.example.oauthserver.security.filter.FilterChainExceptionHandlerFilter;
import com.example.oauthserver.security.filter.RequestBlockingFilter;
import com.example.oauthserver.security.handler.OAuthAccessDefinedHandler;
import com.example.oauthserver.security.handler.OAuthAuthorizationDeniedExceptionHandler;
import com.example.oauthserver.security.utils.RequestMatcherUtils;
import com.example.oauthserver.stereotype.SfcBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@RequiredArgsConstructor
public class OAuthAuthServerSfcConfig {

    @SfcBean
    public SecurityFilterChain oauthAuthServerSfc(
        HttpSecurity http,
        @Qualifier(OAuthAuthServerSfcBeansConfig.OAUTH_BLOCKLIST_REQUEST_MATCHER)
        RequestMatcher requestMatcher,
        RequestBlockingFilter requestBlockingFilter,
        FilterChainExceptionHandlerFilter filterChainExceptionHandlerFilter,
        OAuthAccessDefinedHandler oAuthAccessDefinedHandler,
        OAuthAuthorizationDeniedExceptionHandler oAuthAuthorizationDeniedExceptionHandler,
        RegisteredClientRepository registeredClientRepository
    ) throws Exception {
        OAuth2AuthorizationServerConfigurer authServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        CommonSfcRules
            .applyCommonSettings(http)
            .securityMatcher(
                RequestMatcherUtils.anyOf(
                    authServerConfigurer.getEndpointsMatcher(),
                    requestMatcher
                )
            )
            .with(authServerConfigurer, authServer -> authServer
                .authorizationServerSettings(AuthorizationServerSettings.builder().build())
                .registeredClientRepository(registeredClientRepository)
            )
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/oauth2/device_authorization",
                    "/oauth2/device_verification"
                ).denyAll()
                .anyRequest().authenticated()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(oAuthAuthorizationDeniedExceptionHandler)
                .accessDeniedHandler(oAuthAccessDefinedHandler)
            )
            .formLogin(AbstractHttpConfigurer::disable)
            .addFilterBefore(requestBlockingFilter, SecurityContextHolderFilter.class)
            .addFilterBefore(filterChainExceptionHandlerFilter, RequestBlockingFilter.class);
        return http.build();
    }
}
