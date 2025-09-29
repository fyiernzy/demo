package com.example.oauthserver.client.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateOauthClientRequest(
    @NotNull @NotBlank String clientId,
    @NotNull @NotBlank String clientName,
    @NotNull @NotEmpty Set<String> scopes
) {

}
