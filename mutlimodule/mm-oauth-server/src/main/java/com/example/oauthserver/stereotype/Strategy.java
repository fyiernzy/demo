package com.example.oauthserver.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@Component
public @interface Strategy {

    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";
}
