package com.example.oauthserver.client.application.dto;

import lombok.Data;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Set;

@Data
public class CreateOauthClientResult {

    private String clientId;
    private Long clientIdIssuedAt;
    private String clientSecret;
    private Long clientSecretExpiresAt;
    private String clientName;
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
    private Set<String> grantTypes;
    private Set<String> scopes;
}
