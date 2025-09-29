package com.example.oauthserver.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class RequestBlockingFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher;

    public RequestBlockingFilter(RequestMatcher requestMatcher) {
        this.requestMatcher = Objects.requireNonNull(requestMatcher,
            "requestMatcher must not be null");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
        throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            throw new FilterException(request);
        }
        filterChain.doFilter(request, response);
    }
}
