package com.example.mmecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/e-commerce")
public class ECommerceController {

    @GetMapping("/read")
    public String read() {
        return "ECommerce";
    }
}
