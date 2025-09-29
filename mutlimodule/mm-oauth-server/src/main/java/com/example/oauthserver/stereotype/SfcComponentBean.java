package com.example.oauthserver.stereotype;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/*
 * Use @SfcBean to register a SecurityFilterChain related beans.
 * Besides providing more semantic meaning, a Filter that extends OncePerRequestFilter
 * or GenericBeanFilter and annotated with @SfcBean will have extra processing to disable its registration
 * in the servlet container, avoid duplicating filters.
 * It enforces to specify a name
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Bean
public @interface SfcComponentBean {

    @AliasFor(annotation = Bean.class, attribute = "value")
    String[] value();
}
