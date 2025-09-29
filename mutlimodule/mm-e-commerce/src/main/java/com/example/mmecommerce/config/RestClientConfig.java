package com.example.mmecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

//    @Bean
//    public OAuth2AuthorizedClientManager authorizedClientManager(
//        ClientRegistrationRepository clientRegistrationRepository,
//        OAuth2AuthorizedClientService authorizedClientService) {
//
//        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
//            new AuthorizedClientServiceOAuth2AuthorizedClientManager(
//                clientRegistrationRepository, authorizedClientService);
//
//        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder()
//            .clientCredentials()
//            .build();
//
//        manager.setAuthorizedClientProvider(provider);
//        return manager;
//    }

    @Bean
    public RestClient restClient(RestClient.Builder builder,
                                 OAuth2AuthorizedClientManager manager) {
        OAuth2ClientHttpRequestInterceptor interceptor =
            new OAuth2ClientHttpRequestInterceptor(manager);
        return builder.requestInterceptor(interceptor).build();
    }
}
