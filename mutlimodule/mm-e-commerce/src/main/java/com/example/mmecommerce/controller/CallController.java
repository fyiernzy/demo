package com.example.mmecommerce.controller;

import com.example.mmecommerce.feign.IamApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequiredArgsConstructor
public class CallController {

    private final RestClient restClient;
    private final IamApiService iamApiService;

    @GetMapping("/public/call")
    public String call() {
//        return restClient.get()
//            .uri("http://localhost:9090/api/read")
//            .attributes(RequestAttributeClientRegistrationIdResolver.clientRegistrationId("api-client"))
//            .retrieve()
//            .body(String.class);
        return iamApiService.read();
    }
}

