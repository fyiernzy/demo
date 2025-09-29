package com.example.oauthserver.boot.helper;

import com.example.oauthserver.boot.BootClientAuthenticationMethod;
import com.example.oauthserver.boot.BootOauthClient;
import com.example.oauthserver.stereotype.Strategy;
import com.example.oauthserver.util.environment.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@Strategy
@RequiredArgsConstructor
class ClientSecretBasicStrategy implements ClientAuthenticationStrategy {

    private final PasswordEncoder passwordEncoder;
    private final Profile profile;

    @Override
    public BootClientAuthenticationMethod supports() {
        return BootClientAuthenticationMethod.CLIENT_SECRET_BASIC;
    }

    @Override
    public void apply(RegisteredClient.Builder builder, BootOauthClient client) {
        builder
            .clientAuthenticationMethod(supports().getClientAuthenticationMethod())
            .clientSecret(
                // Encrypt the secret only at live
                profile.isLive()
                ? passwordEncoder.encode(client.secret())
                : client.secret()
            );
    }
}

