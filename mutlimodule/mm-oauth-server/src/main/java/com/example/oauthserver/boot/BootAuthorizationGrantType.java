package com.example.oauthserver.boot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Getter
@RequiredArgsConstructor
public enum BootAuthorizationGrantType {
    AUTHORIZATION_CODE(AuthorizationGrantType.AUTHORIZATION_CODE),
    REFRESH_TOKEN(AuthorizationGrantType.REFRESH_TOKEN),
    CLIENT_CREDENTIALS(AuthorizationGrantType.CLIENT_CREDENTIALS),
    JWT_BEARER(AuthorizationGrantType.JWT_BEARER),
    DEVICE_CODE(AuthorizationGrantType.DEVICE_CODE),
    TOKEN_EXCHANGE(AuthorizationGrantType.TOKEN_EXCHANGE);

    private final AuthorizationGrantType authorizationGrantType;
}
