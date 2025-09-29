package com.example.oauthserver.client.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OauthClientRepositoryImpl implements OauthClientRepository {

    private final RegisteredClientRepository registeredClientRepository;

    @Override
    public RegisteredClient save(RegisteredClient registeredClient) {
        registeredClientRepository.save(registeredClient);
        return registeredClient;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return registeredClientRepository.findByClientId(clientId);
    }

    @Override
    public Page<RegisteredClient> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public boolean existByClientId(String clientId) {
        return registeredClientRepository.findByClientId(clientId) != null;
    }
}
