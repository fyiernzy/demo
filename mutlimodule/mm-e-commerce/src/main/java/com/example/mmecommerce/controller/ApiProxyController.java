package com.example.mmecommerce.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/public")
public class ApiProxyController {

    private final RestClient rest;

    public ApiProxyController(RestClient rest) {
        this.rest = rest;
    }

    @GetMapping("/call-api")
    public String callApi(
        @RegisteredOAuth2AuthorizedClient("oidc-client") OAuth2AuthorizedClient client) {

        return rest.get()
            .uri("http://localhost:9090/api/read")
            .headers(h -> h.setBearerAuth(client.getAccessToken().getTokenValue()))
            .retrieve()
            .body(String.class);
    }
}

