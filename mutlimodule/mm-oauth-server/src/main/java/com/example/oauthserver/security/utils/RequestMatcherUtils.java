package com.example.oauthserver.security.utils;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.*;
import java.util.stream.Collectors;

public final class RequestMatcherUtils {

    private RequestMatcherUtils() {
    }

    private static PathPatternRequestMatcher.Builder defaultMatcherBuilder() {
        return PathPatternRequestMatcher.withDefaults();
    }

    public static RequestMatcher anyOf(String... pathPatterns) {
        return anyOf(pathMatchers(pathPatterns));
    }

    public static RequestMatcher anyOf(List<String> pathPatterns) {
        return anyOf(pathMatchers(pathPatterns));
    }

    public static RequestMatcher anyOf(RequestMatcher... matchers) {
        return (matchers.length > 0) ? new OrRequestMatcher(List.of(matchers)) : (request) -> false;
    }

    public static RequestMatcher anyOf(@NonNull Collection<RequestMatcher> matchers) {
        return anyOf(matchers.toArray(RequestMatcher[]::new));
    }

    public static RequestMatcher allOf(String... pathPatterns) {
        return allOf(pathMatchers(pathPatterns));
    }

    public static RequestMatcher allOf(List<String> pathPatterns) {
        return allOf(pathMatchers(pathPatterns));
    }

    public static RequestMatcher allOf(@NonNull RequestMatcher... matchers) {
        return (matchers.length > 0) ? new AndRequestMatcher(List.of(matchers)) : (request) -> true;
    }

    public static RequestMatcher allOf(@NonNull Collection<RequestMatcher> matchers) {
        return allOf(matchers.toArray(RequestMatcher[]::new));
    }

    public static RequestMatcher not(@NonNull RequestMatcher matcher) {
        return (request) -> !matcher.matches(request);
    }

    public static List<RequestMatcher> pathMatchers(@NonNull String... pathPatterns) {
        return pathMatchers(null, pathPatterns);
    }

    public static List<RequestMatcher> pathMatchers(@Nullable HttpMethod httpMethod,
                                                    @NonNull String... pathPatterns) {
        return Arrays.asList(pathMatchersAsArray(httpMethod, pathPatterns));
    }


    public static List<RequestMatcher> pathMatchers(@NonNull List<String> pathPatterns) {
        return pathMatchers(null, pathPatterns);
    }

    public static List<RequestMatcher> pathMatchers(@Nullable HttpMethod httpMethod,
                                                    @NonNull List<String> pathPatterns) {
        return pathPatterns.stream()
            .map(pathPattern -> defaultMatcherBuilder().matcher(httpMethod, pathPattern))
            .collect(Collectors.toUnmodifiableList());
    }

    public static List<RequestMatcher> pathMatchersAsList(@NonNull String... pathPatterns) {
        return pathMatchersAsList(null, pathPatterns);
    }

    public static List<RequestMatcher> pathMatchersAsList(@Nullable HttpMethod httpMethod,
                                                          @NonNull String... pathPatterns) {
        List<RequestMatcher> matchers = new ArrayList<>();
        PathPatternRequestMatcher.Builder builder = defaultMatcherBuilder();
        for (String pathPattern : pathPatterns) {
            matchers.add(builder.matcher(httpMethod, pathPattern));
        }
        return List.copyOf(matchers);
    }

    public static RequestMatcher[] pathMatchersAsArray(@NonNull String... pathPatterns) {
        return pathMatchersAsArray(null, pathPatterns);
    }

    public static RequestMatcher[] pathMatchersAsArray(@Nullable HttpMethod httpMethod,
                                                       @NonNull String... pathPatterns) {
        RequestMatcher[] matchers = new RequestMatcher[pathPatterns.length];
        PathPatternRequestMatcher.Builder builder = defaultMatcherBuilder();
        for (int index = 0; index < pathPatterns.length; index++) {
            matchers[index] = builder.matcher(httpMethod, pathPatterns[index]);
        }
        return matchers;
    }

    public static List<RequestMatcher> pathMatchersAsList(@NonNull List<String> pathPatterns) {
        return pathMatchersAsList(null, pathPatterns);
    }

    public static List<RequestMatcher> pathMatchersAsList(@Nullable HttpMethod httpMethod,
                                                          @NonNull List<String> pathPatterns) {
        if (pathPatterns.isEmpty()) {
            return List.of();
        }
        final PathPatternRequestMatcher.Builder builder = defaultMatcherBuilder();
        final List<RequestMatcher> list = new ArrayList<>();
        for (String pathPattern : pathPatterns) {
            list.add(builder.matcher(httpMethod, pathPattern));
        }
        return List.copyOf(list);
    }

    public static RequestMatcher[] pathMatchersAsArray(@NonNull List<String> pathPatterns) {
        return pathMatchersAsArray(null, pathPatterns);
    }

    public static RequestMatcher[] pathMatchersAsArray(@Nullable HttpMethod httpMethod,
                                                       @NonNull List<String> pathPatterns) {
        final int size = pathPatterns.size();
        RequestMatcher[] matchers = new RequestMatcher[size];
        if (size == 0) {
            return matchers;
        }
        final var builder = defaultMatcherBuilder();
        for (int i = 0; i < size; i++) {
            matchers[i] = builder.matcher(httpMethod, pathPatterns.get(i));
        }
        return matchers;
    }

}
