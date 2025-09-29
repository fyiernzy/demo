package com.example.mmecommerce.common;

import com.example.mmecommerce.common.infra.ErrorCode;
import com.example.mmecommerce.common.infra.ErrorCodeRegistry;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorCodeRegistry registry;
    private final RequestInfo requestInfo;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return handleValidationException(ex, buildViolationMap(ex));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolation(ConstraintViolationException ex) {
        return handleValidationException(ex, buildViolationMap(ex));
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGeneralException(Exception ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        detail.setTitle("Unexpected error occurred");
        detail.setDetail(ex.getMessage()); // be careful: may expose internals in prod
        return ErrorResponse.builder(ex, detail).build();
    }

    private ErrorResponse handleValidationException(Exception ex, Map<String, String> violations) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setInstance(URI.create(requestInfo.getRequestURI()));
        problemDetail.setProperty("errors", violations);
        problemDetail.setProperty("method", requestInfo.getMethod());
        return ErrorResponse.builder(ex, problemDetail).build();
    }

    private Map<String, String> buildViolationMap(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errors.put(field, buildMessage(field, message));
        }
        return errors;
    }

    private Map<String, String> buildViolationMap(ConstraintViolationException ex) {
        Map<String, String> constraintViolations = new HashMap<>();
        for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
            String field = v.getPropertyPath() != null ? v.getPropertyPath().toString() : null;
            String message = v.getMessage();
            constraintViolations.put(field, buildMessage(field, message));
        }
        return constraintViolations;
    }

    private String buildMessage(String field, String message) {
        ErrorCode errorCode = registry.resolveByCode(message);
        // 1. If it's not a customized validation annotation, use the message directly
        if (errorCode == null) {
            return field + ": " + message;
        }
        // 2. Otherwise, use information carried by the ErrorCode.
        return String.format("%s: %s#%s#%s", field, errorCode.getCode(), errorCode.getName(),
            errorCode.getMessage());
    }
}
