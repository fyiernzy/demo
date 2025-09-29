package com.example.oauthserver.controller;

public record ClientPostRegistrationModel(
    String clientId,
    String clientSecret
) {

}
