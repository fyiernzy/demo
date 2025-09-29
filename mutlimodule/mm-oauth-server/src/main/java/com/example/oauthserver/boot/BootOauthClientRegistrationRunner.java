package com.example.oauthserver.boot;

import com.example.oauthserver.boot.helper.ClientAuthenticationStrategy;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableConfigurationProperties(BootOauthClientProperties.class)
public class BootOauthClientRegistrationRunner {

    @Bean
    ApplicationRunner clientRegistration(
        BootOauthClientProperties bootOauthClientProperties,
        RegisteredClientRepository registeredClientRepository,
        List<ClientAuthenticationStrategy> clientAuthenticationStrategies
    ) {
        Map<BootClientAuthenticationMethod, ClientAuthenticationStrategy> strategyIndex =
            indexStrategies(clientAuthenticationStrategies);

        return args -> {
            for (Map.Entry<String, BootOauthClient> clientEntry : bootOauthClientProperties.clients()
                .entrySet()) {
                BootOauthClient client = clientEntry.getValue();
                if (registeredClientRepository.findByClientId(client.clientId()) != null) {
                    continue;
                }

                RegisteredClient.Builder builder = RegisteredClient
                    .withId(UUID.randomUUID().toString())
                    .clientId(client.clientId())
                    .clientName(client.clientName());
                applyAuth(builder, client, strategyIndex);
                applyGrants(builder, client);
                applyScopes(builder, client);
                applyRedirectUris(builder, client);
                applyTimeStamps(builder, client);
                applyTokenSettings(builder);
                registeredClientRepository.save(builder.build());
            }
        };
    }

    private Map<BootClientAuthenticationMethod, ClientAuthenticationStrategy> indexStrategies(
        List<ClientAuthenticationStrategy> strategies
    ) {
        Map<BootClientAuthenticationMethod, ClientAuthenticationStrategy> index =
            new EnumMap<>(BootClientAuthenticationMethod.class);
        for (ClientAuthenticationStrategy strategy : strategies) {
            index.put(strategy.supports(), strategy);
        }
        return index;
    }

    private void applyAuth(
        RegisteredClient.Builder builder,
        BootOauthClient client,
        Map<BootClientAuthenticationMethod, ClientAuthenticationStrategy> strategyIndex
    ) {
        ClientAuthenticationStrategy strategy = strategyIndex.get(client.authMethod());
        Assert.notNull(strategy, "Unsupported auth method " + client.authMethod());
        strategy.apply(builder, client);
    }

    private void applyGrants(RegisteredClient.Builder builder, BootOauthClient client) {
        builder.authorizationGrantTypes(set -> {
            set.clear();
            for (BootAuthorizationGrantType grant : client.grants()) {
                set.add(grant.getAuthorizationGrantType());
            }
        });
    }

    private void applyScopes(RegisteredClient.Builder builder, BootOauthClient client) {
        builder.scopes(set -> {
            set.clear();
            set.addAll(client.scopes());
        });
    }

    private void applyRedirectUris(RegisteredClient.Builder builder, BootOauthClient client) {
        if (client.redirectUris() == null) {
            return;
        }
        builder.redirectUris(set -> {
            set.clear();
            set.addAll(client.redirectUris());
        });
    }

    private void applyTimeStamps(RegisteredClient.Builder builder, BootOauthClient client) {
        builder.clientIdIssuedAt(Instant.now());
    }

    private void applyTokenSettings(RegisteredClient.Builder builder) {
        TokenSettings tokenSettings = TokenSettings.builder()
            .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
            .accessTokenTimeToLive(Duration.ofMinutes(10))
            .refreshTokenTimeToLive(Duration.ofDays(10))
            .reuseRefreshTokens(false)
            .build();
        builder.tokenSettings(tokenSettings);
    }

}
