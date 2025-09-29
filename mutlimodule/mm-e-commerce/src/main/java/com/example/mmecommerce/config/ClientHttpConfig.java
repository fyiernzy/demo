//package com.example.mmecommerce.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.oauth2.client.*;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
//import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
//import org.springframework.web.client.RestClient;
//
//@Configuration
//@EnableMethodSecurity
//public class ClientHttpConfig {
//
//    @Configuration
//    public class ClientOAuthConfig {
//
//        @Bean
//        OAuth2AuthorizedClientManager authorizedClientManager(
//            ClientRegistrationRepository clientRegistrationRepository,
//            OAuth2AuthorizedClientService authorizedClientService) {
//
//            OAuth2AuthorizedClientProvider provider =
//                OAuth2AuthorizedClientProviderBuilder.builder()
//                    .clientCredentials()
//                    .build();
//
//            AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
//                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
//                    clientRegistrationRepository, authorizedClientService);
//
//            manager.setAuthorizedClientProvider(provider);
//            return manager;
//        }
//    }
//
//
//    @Bean
//    RestClient restClient() {
//        return RestClient.create();
//    }
//}
//
