package com.example.oauthserver.boot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Getter
@RequiredArgsConstructor
public enum BootClientAuthenticationMethod {
    CLIENT_SECRET_BASIC(ClientAuthenticationMethod.CLIENT_SECRET_BASIC),
    PRIVATE_KEY_JWT(ClientAuthenticationMethod.PRIVATE_KEY_JWT),
    TLS_CLIENT_AUTH(ClientAuthenticationMethod.TLS_CLIENT_AUTH),
    CLIENT_SECRET_JWT(ClientAuthenticationMethod.CLIENT_SECRET_JWT),
    CLIENT_SECRET_POST(ClientAuthenticationMethod.CLIENT_SECRET_POST),
    SELF_SIGNED_TLS_CLIENT_AUTH(ClientAuthenticationMethod.SELF_SIGNED_TLS_CLIENT_AUTH);

    private final ClientAuthenticationMethod clientAuthenticationMethod;
}
