package com.example.oauthserver.util.environment;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Profile {

    public static final String LIVE = "live";
    public static final String LOCAL = "local";

    private final List<String> profiles;

    public Profile(Environment environment) {
        this.profiles = Arrays.stream(environment.getActiveProfiles()).toList();
    }

    public boolean isLive() {
        return profiles.contains("live") || profiles.contains("prod");
    }
}
