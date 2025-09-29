package com.example.oauthserver.stereotype;

import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Bean
@Order
public @interface SfcBean {

    @AliasFor(annotation = Order.class, value = "value")
    int order() default Ordered.LOWEST_PRECEDENCE - 1;
}
