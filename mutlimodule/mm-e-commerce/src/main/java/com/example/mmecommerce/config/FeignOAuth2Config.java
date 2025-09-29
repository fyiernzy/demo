package com.example.mmecommerce.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;

import java.util.List;
import java.util.Optional;

@Configuration
public class FeignOAuth2Config {

//    @Bean
//    public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2AuthorizedClientManager manager) {
//        return template -> {
//            // 1. Use a fake identity
//            Authentication principal = new AnonymousAuthenticationToken(
//                "k", "u", List.of(
//                new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
//
//            OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
//                .withClientRegistrationId("api-client")
//                .principal(principal)
//                .build();
//
//            OAuth2AuthorizedClient oAuth2AuthorizedClient = manager.authorize(
//                oAuth2AuthorizeRequest);
//
//            String token = Optional.ofNullable(oAuth2AuthorizedClient)
//                .map(OAuth2AuthorizedClient::getAccessToken)
//                .map(AbstractOAuth2Token::getTokenValue)
//                .orElse("");
//
//            template.header("Authorization", "Bearer " + token);
//        };
//    }
}
