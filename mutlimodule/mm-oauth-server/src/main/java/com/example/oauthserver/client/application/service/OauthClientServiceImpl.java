package com.example.oauthserver.client.application.service;

import com.example.oauthserver.client.application.dto.CreateOauthClientRequest;
import com.example.oauthserver.client.application.dto.CreateOauthClientResult;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OauthClientServiceImpl implements OauthClientService {

    private final OauthClientRepository oauthClientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public @NonNull CreateOauthClientResult addClient(@NonNull CreateOauthClientRequest createOauthClientRequest) {
        boolean isNewClient = !oauthClientRepository.existByClientId(
            createOauthClientRequest.clientId());
        Assert.state(isNewClient, "The client id has been registered.");

        CreateOauthClientResult createOauthClientResult = new CreateOauthClientResult();
        fillCreateOauthClientResult(createOauthClientResult, createOauthClientRequest);

        RegisteredClient registeredClient = buildRegisteredClient(createOauthClientResult);
        oauthClientRepository.save(registeredClient);
        fillCreateOauthClientResult(createOauthClientResult, registeredClient);
        return createOauthClientResult;
    }

    private void fillCreateOauthClientResult(CreateOauthClientResult createOauthClientResult,
                                             CreateOauthClientRequest createOauthClientRequest) {
        String secret = UUID.randomUUID().toString();
        createOauthClientResult.setClientId(createOauthClientRequest.clientId());
        createOauthClientResult.setClientSecret(secret);
        createOauthClientResult.setClientName(createOauthClientRequest.clientName());
        createOauthClientResult.setScopes(createOauthClientRequest.scopes());
    }

    private void fillCreateOauthClientResult(CreateOauthClientResult createOauthClientResult,
                                             RegisteredClient registeredClient) {
        createOauthClientResult.setClientIdIssuedAt(
            Optional.ofNullable(registeredClient.getClientIdIssuedAt())
                .map(Instant::getEpochSecond)
                .orElse(null)
        );
        createOauthClientResult.setClientSecretExpiresAt(
            Optional.ofNullable(registeredClient.getClientSecretExpiresAt())
                .map(Instant::getEpochSecond)
                .orElse(null)
        );
        createOauthClientResult.setGrantTypes(
            registeredClient.getAuthorizationGrantTypes().stream()
                .map(AuthorizationGrantType::getValue)
                .collect(Collectors.toSet())
        );
    }

    private RegisteredClient buildRegisteredClient(CreateOauthClientResult createOauthClientResult) {
        return RegisteredClient
            .withId(UUID.randomUUID().toString())
            .clientId(createOauthClientResult.getClientId())
            .clientSecret(passwordEncoder.encode(createOauthClientResult.getClientSecret()))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .scopes(scopes -> scopes.addAll(createOauthClientResult.getScopes()))
            .build();
    }

}
