package com.example.mmecommerce.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
@RequiredArgsConstructor

public class RequestInfo {

    @Delegate
    private final HttpServletRequest httpServletRequest;

}
