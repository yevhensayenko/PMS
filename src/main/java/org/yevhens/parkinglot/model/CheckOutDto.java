package org.yevhens.parkinglot.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CheckOutDto(
        @NotNull
        @Min(1)
        Long receiptId
) {
}
