package com.example.oauthserver.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;

import java.time.Instant;

public class FilterException extends RuntimeException {

    private final String path;
    private final String method;
    private final Instant timestamp = Instant.now();

    public FilterException(@NonNull HttpServletRequest request) {
        super(buildMessage(request));
        this.path = request.getRequestURI();
        this.method = request.getMethod();
    }

    public FilterException(@NonNull HttpServletRequest request, String reason) {
        super(buildMessage(request) + (reason != null ? " :: " + reason : ""));
        this.path = request.getRequestURI();
        this.method = request.getMethod();
    }

    private static String buildMessage(HttpServletRequest req) {
        return "Blocked request: %s %s".formatted(req.getMethod(), req.getRequestURI());
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
