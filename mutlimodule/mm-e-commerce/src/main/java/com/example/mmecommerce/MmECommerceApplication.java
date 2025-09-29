package com.example.mmecommerce;

import com.example.mmecommerce.common.infra.EnableErrorCodeRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableErrorCodeRegistry
@SpringBootApplication
@EnableFeignClients
public class MmECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmECommerceApplication.class, args);
    }

}
