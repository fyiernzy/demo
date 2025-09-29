package com.example.oauthserver.client.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface OauthClientRepository {

    RegisteredClient save(RegisteredClient registeredClient);

    RegisteredClient findByClientId(String clientId);

    Page<RegisteredClient> findAll(Pageable pageable);

    boolean existByClientId(String clientId);
}
