package com.example.mmecommerce.common;

import com.example.mmecommerce.common.infra.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@NotBlank
@RequiredArgsConstructor
@RegisterErrorCode
public enum FxErrorCodeEnum implements ErrorCode {

    FXTS_RATE_NOT_FOUND(Code.FXTS_RATE_NOT_FOUND, "fxts.rate.not.found"),
    FEE_TIER_NOT_FOUND(Code.FEE_TIER_NOT_FOUND, "fee.tier.not.found"),
    INVALID_TRANSACTION_AMOUNT(Code.INVALID_TRANSACTION_AMOUNT, "invalid.transaction.amount"),
    FXTS_ORDER_FAILED(Code.FXTS_ORDER_FAILED, "fxts.order.failed"),
    LOCK_FX_TRANSACTION_NOT_FOUND(Code.LOCK_FX_TRANSACTION_NOT_FOUND,
        "lock.fx.transaction.not.found"),
    INVALID_REQUEST(Code.INVALID_REQUEST, "invalid.request"),
    SELL_CURRENCY_CODE_NOT_FOUND(Code.SELL_CURRENCY_CODE_NOT_FOUND, "sell.currency.code.not.found"),
    BUY_CURRENCY_CODE_NOT_FOUND(Code.BUY_CURRENCY_CODE_NOT_FOUND, "buy.currency.code.not.found"),
    ACCOUNT_NO_NOT_FOUND(Code.ACCOUNT_NO_NOT_FOUND, "account.no.not.found"),
    E_WALLET_ACCOUNT_NOT_ENOUGH_BALANCE(Code.E_WALLET_ACCOUNT_NOT_ENOUGH_BALANCE,
        "e.wallet.account.not.enough.balance"),
    SYSTEM_RATE_NOT_FOUND(Code.SYSTEM_RATE_NOT_FOUND, "system.rate.not.found"),
    UNSUPPORTED_CURRENCY_DECIMAL(Code.UNSUPPORTED_CURRENCY_DECIMAL, "unsupported.currency.decimal"),
    FX_TRANSACTION_NOT_FOUND(Code.FX_TRANSACTION_NOT_FOUND, "fx.transaction.not.found"),
    CURRENCY_CODE_NOT_FOUND(Code.CURRENCY_CODE_NOT_FOUND, "currency.code.not.found"),
    INVALID_FX_TRANSACTION_TYPE(Code.INVALID_FX_TRANSACTION_TYPE, "invalid.fx.transaction.type"),
    E_WALLET_ACCOUNT_TRANSACTION_ID_NOT_FOUND(Code.E_WALLET_ACCOUNT_TRANSACTION_ID_NOT_FOUND,
        "e.wallet.account.transaction.id.not.found");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static class Code {

        public static final String FXTS_RATE_NOT_FOUND = "FX001";
        public static final String FEE_TIER_NOT_FOUND = "FX002";
        public static final String INVALID_TRANSACTION_AMOUNT = "FX003";
        public static final String FXTS_ORDER_FAILED = "FX004";
        public static final String LOCK_FX_TRANSACTION_NOT_FOUND = "FX005";
        public static final String INVALID_REQUEST = "FX006";
        public static final String SELL_CURRENCY_CODE_NOT_FOUND = "FX007";
        public static final String BUY_CURRENCY_CODE_NOT_FOUND = "FX008";
        public static final String ACCOUNT_NO_NOT_FOUND = "FX009";
        public static final String E_WALLET_ACCOUNT_NOT_ENOUGH_BALANCE = "FX010";
        public static final String SYSTEM_RATE_NOT_FOUND = "FX011";
        public static final String UNSUPPORTED_CURRENCY_DECIMAL = "FX012";
        public static final String FX_TRANSACTION_NOT_FOUND = "FX013";
        public static final String CURRENCY_CODE_NOT_FOUND = "FX014";
        public static final String INVALID_FX_TRANSACTION_TYPE = "FX015";
        public static final String E_WALLET_ACCOUNT_TRANSACTION_ID_NOT_FOUND = "FX016";
    }
}
