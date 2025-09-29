package com.example.mmecommerce.common;

import jakarta.validation.Valid;

public interface FxService {

    void process(@Valid CreateFxLockRequestModel requestModel);
}
