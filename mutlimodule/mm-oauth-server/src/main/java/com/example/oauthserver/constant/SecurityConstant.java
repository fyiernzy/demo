package com.example.oauthserver.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class SecurityConstant {

    public static final String PROPERTY_FILE_PREFIX = "security";
    public static final String OAUTH_BLOCKLIST = "security.oauth.blocklist";
}
