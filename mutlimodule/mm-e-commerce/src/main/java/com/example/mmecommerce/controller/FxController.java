package com.example.mmecommerce.controller;

import com.example.mmecommerce.common.CreateFxLockRequestModel;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/fx")
public class FxController {

    @PostMapping("/create")
    public String hello(@RequestBody @Valid CreateFxLockRequestModel createFxLockRequestModel) {
        System.out.println(createFxLockRequestModel);
        return "Everything just good";
    }
}
