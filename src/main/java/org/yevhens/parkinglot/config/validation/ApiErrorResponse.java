package org.yevhens.parkinglot.config.validation;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        Instant timestamp,
        String message,
        List<FieldValidationError> errors
) {
}