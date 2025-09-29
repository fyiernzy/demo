package com.example.oauthserver.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;


@ConfigurationProperties(prefix = "auth")
public record BootOauthClientProperties(
    @NestedConfigurationProperty
    Map<String, BootOauthClient> clients
) {


}
