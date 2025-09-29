package com.example.oauthserver.boot.helper;

import com.example.oauthserver.boot.BootClientAuthenticationMethod;
import com.example.oauthserver.boot.BootOauthClient;
import com.example.oauthserver.stereotype.Strategy;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@Strategy
class TlsClientAuthStrategy implements ClientAuthenticationStrategy {

    @Override
    public BootClientAuthenticationMethod supports() {
        return BootClientAuthenticationMethod.TLS_CLIENT_AUTH;
    }

    @Override
    public void apply(RegisteredClient.Builder builder, BootOauthClient client) {
        builder.clientAuthenticationMethod(supports().getClientAuthenticationMethod());
    }
}

