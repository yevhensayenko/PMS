package org.yevhens.parkinglot.config.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yevhens.parkinglot.exception.NoAvailableSpotsException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        List<FieldValidationError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapFieldError)
                .toList();

        return new ApiErrorResponse(Instant.now(), "Request validation failed", fieldErrors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiErrorResponse handleMethodArgumentNotValid(ConstraintViolationException ex) {

        List<FieldValidationError> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(this::mapFieldError)
                .toList();

        return new ApiErrorResponse(Instant.now(), "Request validation failed", fieldErrors);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NoAvailableSpotsException.class)
    public ApiErrorResponse handleMethodArgumentNotValid() {
        return new ApiErrorResponse(Instant.now(), "No available spots at this moment", null);
    }

    private FieldValidationError mapFieldError(FieldError error) {
        return new FieldValidationError(error.getField(), error.getDefaultMessage());
    }

    private FieldValidationError mapFieldError(ConstraintViolation<?> violation) {
        return new FieldValidationError(violation.getPropertyPath().toString(), violation.getMessage());
    }
}
