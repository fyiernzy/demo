package com.example.oauthserver.security.chain;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class CommonSfcRules {

    /*
     * Respect the fluent style
     */
    public static HttpSecurity applyCommonSettings(HttpSecurity http) throws Exception {
        return http;
    }
}
