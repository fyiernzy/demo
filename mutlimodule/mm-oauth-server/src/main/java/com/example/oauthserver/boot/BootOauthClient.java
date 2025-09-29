package com.example.oauthserver.boot;

import java.util.List;

public record BootOauthClient(
    String clientId,
    String clientName,
    BootClientAuthenticationMethod authMethod,
    List<BootAuthorizationGrantType> grants,
    List<String> scopes,
    List<String> redirectUris,
    String secret,
    String jwksUri,
    String tokenEndpointAlg
) {

}
