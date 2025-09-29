package com.example.oauthserver.boot.helper;

import com.example.oauthserver.boot.BootClientAuthenticationMethod;
import com.example.oauthserver.boot.BootOauthClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface ClientAuthenticationStrategy {

    BootClientAuthenticationMethod supports();

    void apply(RegisteredClient.Builder builder, BootOauthClient client);
}
