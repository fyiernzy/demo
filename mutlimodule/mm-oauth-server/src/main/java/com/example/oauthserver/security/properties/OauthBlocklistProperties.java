package com.example.oauthserver.security.properties;

import com.example.oauthserver.constant.SecurityConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = SecurityConstant.OAUTH_BLOCKLIST)
public record OauthBlocklistProperties(
    List<String> path
) {

}
