package org.yevhens.parkinglot.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CheckOutDto(
        @NotNull
        @Positive
        Long orderId
) {
}
