package com.example.mmecommerce.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "mm-iam")
public interface IamApiService {

    @GetMapping("/api/read")
    String read();
}
