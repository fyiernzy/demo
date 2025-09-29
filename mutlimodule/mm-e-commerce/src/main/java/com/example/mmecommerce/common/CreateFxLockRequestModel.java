package com.example.mmecommerce.common;

import com.example.mmecommerce.common.validation.NotBlank;

public record CreateFxLockRequestModel(
    @NotBlank(code = FxErrorCodeEnum.Code.ACCOUNT_NO_NOT_FOUND)
    String refno
) {

}
