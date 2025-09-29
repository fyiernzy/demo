package com.example.mmecommerce.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public abstract class AbstractValidator<A extends Annotation, T> implements
    ConstraintValidator<A, T> {

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        boolean valid = isValid(value);
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context
                .buildConstraintViolationWithTemplate(code())
                .addConstraintViolation();
        }
        return valid;
    }

    protected abstract String code();

    protected abstract boolean isValid(T value);
}

