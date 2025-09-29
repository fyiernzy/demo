package com.example.oauthserver.client.api;

import com.example.oauthserver.client.application.dto.CreateOauthClientRequest;
import com.example.oauthserver.client.application.dto.CreateOauthClientResult;
import com.example.oauthserver.client.application.service.OauthClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/client")
public class ClientRegistrationController {

    private final OauthClientService oauthClientService;

    @PostMapping("/create")
    public CreateOauthClientResult createClient(@Valid @RequestBody CreateOauthClientRequest clientRegistrationModel) {
        return oauthClientService.addClient(clientRegistrationModel);
    }

//    private RegisteredClient buildRegisteredClient(ClientRegistrationModel clientRegistrationModel) {
//        String secret = UUID.randomUUID().toString();
//        return RegisteredClient
//            .withId(UUID.randomUUID().toString())
//            .clientId(clientRegistrationModel.clientId())
//            .clientSecret(passwordEncoder.encode(secret))
//            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//            .scope("api.read")
//            .build();
//    }
}
