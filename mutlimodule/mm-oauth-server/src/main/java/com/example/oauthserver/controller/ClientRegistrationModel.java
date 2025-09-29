package com.example.oauthserver.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRegistrationModel(
    @NotNull @NotBlank String clientId
) {

}
