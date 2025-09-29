package com.example.oauthserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

public class TokenSettingsTest {

    @Test
    void tokenSettings() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TokenSettings tokenSettingsTest = TokenSettings.builder()
            .accessTokenTimeToLive(Duration.ofHours(1))
            .reuseRefreshTokens(false)
            .build();
        mapper.registerModule(new OAuth2AuthorizationServerJackson2Module());

        String json = mapper.writeValueAsString(tokenSettingsTest);
        System.out.println(json);
        Assertions.assertNotNull(json);
    }
}
