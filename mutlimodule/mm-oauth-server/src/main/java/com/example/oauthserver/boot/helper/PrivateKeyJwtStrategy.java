package com.example.oauthserver.boot.helper;

import com.example.oauthserver.boot.BootClientAuthenticationMethod;
import com.example.oauthserver.boot.BootOauthClient;
import com.example.oauthserver.stereotype.Strategy;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@Strategy
class PrivateKeyJwtStrategy implements ClientAuthenticationStrategy {

    @Override
    public BootClientAuthenticationMethod supports() {
        return BootClientAuthenticationMethod.PRIVATE_KEY_JWT;
    }

    @Override
    public void apply(RegisteredClient.Builder builder, BootOauthClient client) {
        builder.clientAuthenticationMethod(supports().getClientAuthenticationMethod());
        ClientSettings.Builder clientSettings = ClientSettings.builder()
            .jwkSetUrl(client.jwksUri());
        if (client.tokenEndpointAlg() != null) {
            clientSettings.tokenEndpointAuthenticationSigningAlgorithm(
                SignatureAlgorithm.from(client.tokenEndpointAlg()));
        }
        builder.clientSettings(clientSettings.build());
    }
}

