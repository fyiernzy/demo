package com.example.mmecommerce.common.validation;

public class NotBlankValidator extends AbstractValidator<NotBlank, String> {

    private String code;

    @Override
    public void initialize(NotBlank constraintAnnotation) {
        this.code = constraintAnnotation.code();
    }

    @Override
    protected String code() {
        return code;
    }

    @Override
    protected boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
