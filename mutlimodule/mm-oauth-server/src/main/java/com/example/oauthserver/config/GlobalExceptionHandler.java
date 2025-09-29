package com.example.oauthserver.config;

import com.example.oauthserver.security.filter.FilterException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fe -> Map.of("field", fe.getField(), "message", fe.getDefaultMessage()))
            .collect(Collectors.toList());

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Validation failed");
        detail.setProperty("errors", errors);
        return ErrorResponse.builder(ex, detail).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolation(ConstraintViolationException ex) {
        List<Map<String, String>> errors = ex.getConstraintViolations()
            .stream()
            .map(cv -> Map.of("path", cv.getPropertyPath().toString(),
                "message", cv.getMessage()))
            .collect(Collectors.toList());

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Validation failed");
        detail.setProperty("errors", errors);
        return ErrorResponse.builder(ex, detail).build();
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGeneralException(Exception ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        detail.setTitle("Unexpected error occurred");
        detail.setDetail(ex.getMessage()); // be careful: may expose internals in prod
        return ErrorResponse.builder(ex, detail).build();
    }

    @ExceptionHandler(FilterException.class)
    public ErrorResponse handleBlockedRequestException(FilterException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        detail.setTitle("Blocked request");
        detail.setDetail(ex.getMessage());
        detail.setProperties(
            Map.of("path", ex.getPath(),
                "method", ex.getMethod())
        );
        return ErrorResponse.builder(ex, detail).build();
    }

}
