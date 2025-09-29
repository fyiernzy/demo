package com.example.mmecommerce.common.infra;

import com.example.mmecommerce.common.stereotype.Registry;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Registry
public class ErrorCodeRegistry {

    private final Map<String, ErrorCode> registry;

    public ErrorCodeRegistry(List<ErrorCode> errorCodes) {
        this.registry = Objects.requireNonNull(errorCodes).stream()
            .collect(Collectors.toMap(ErrorCode::getCode, Function.identity()));
    }

    public @Nullable ErrorCode resolveByCode(String code) {
        return registry.get(code);
    }
}
