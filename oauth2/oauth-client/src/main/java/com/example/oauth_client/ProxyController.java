package com.example.oauth_client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequiredArgsConstructor
public class ProxyController {

    private final WebClient webClient;

    @GetMapping("/proxy/hello")
    public ResponseEntity<String> proxyHello() {
        String body = webClient.get()
            .uri("http://localhost:8081/api/hello")
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return ResponseEntity.ok(body);
    }
}
