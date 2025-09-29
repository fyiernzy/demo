package com.example.oauthserver.client.application.service;

import com.example.oauthserver.client.application.dto.CreateOauthClientRequest;
import com.example.oauthserver.client.application.dto.CreateOauthClientResult;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface OauthClientService {

    @NonNull
    CreateOauthClientResult addClient(@NonNull @Valid CreateOauthClientRequest createOauthClientRequest);
}
