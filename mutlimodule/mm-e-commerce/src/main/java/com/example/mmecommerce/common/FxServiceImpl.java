package com.example.mmecommerce.common;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public class FxServiceImpl implements FxService {

    @Override
    public void process(@Valid CreateFxLockRequestModel requestModel) {
        // do nothing
    }
}
