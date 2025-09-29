-- api-client
INSERT INTO oauth2_registered_client (id, client_id, client_id_issued_at, client_secret,
                                      client_secret_expires_at,
                                      client_name, client_authentication_methods,
                                      authorization_grant_types,
                                      redirect_uris, post_logout_redirect_uris, scopes,
                                      client_settings, token_settings)
VALUES ('3f9b8d64-8d2c-4b0e-9a2e-5db6f2d7b001',
        'api-client',
        CURRENT_TIMESTAMP,
        '{noop}secret1',
        NULL,
        'api-client',
        'client_secret_basic',
        'client_credentials',
        NULL,
        NULL,
        'api.read',
           -- ClientSettings (typed JSON)
        '{"@class":"java.util.Collections$UnmodifiableMap",'
            || '"settings.client.require-proof-key":false,'
            || '"settings.client.require-authorization-consent":false}'
           ,
           -- TokenSettings (typed JSON)
        '{"@class":"java.util.Collections$UnmodifiableMap",'
            || '"settings.token.reuse-refresh-tokens":false,'
            || '"settings.token.x509-certificate-bound-access-tokens":false,'
            || '"settings.token.access-token-format":{'
            ||
        '"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat",'
            || '"value":"self-contained"},'
            || '"settings.token.access-token-time-to-live":["java.time.Duration",3600.000000000]}');

-- api-visa
INSERT INTO oauth2_registered_client (id, client_id, client_id_issued_at, client_secret,
                                      client_secret_expires_at,
                                      client_name, client_authentication_methods,
                                      authorization_grant_types,
                                      redirect_uris, post_logout_redirect_uris, scopes,
                                      client_settings, token_settings)
VALUES ('3f9b8d64-8d2c-4b0e-9a2e-5db6f2d7b002',
        'api-visa',
        CURRENT_TIMESTAMP,
        '{noop}secret2',
        NULL,
        'api-visa',
        'client_secret_basic',
        'client_credentials',
        NULL,
        NULL,
        'api.read',
        '{'
            || '"@class":"java.util.Collections$UnmodifiableMap",'
            || '"settings.client.require-proof-key":false,'
            || '"settings.client.require-authorization-consent":false'
            || '}'
           ,
        '{'
            || '"@class":"java.util.Collections$UnmodifiableMap",'
            || '"settings.token.reuse-refresh-tokens":false,'
            || '"settings.token.x509-certificate-bound-access-tokens":false,'
            || '"settings.token.access-token-format":{'
            ||
        '"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat",'
            || '"value":"self-contained"'
            || '},'
            || '"settings.token.access-token-time-to-live":["java.time.Duration",3600.000000000]}');
