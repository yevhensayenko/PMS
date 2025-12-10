package org.yevhens.parkinglot.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckInDto(
        @NotNull
        @Min(1)
        Long parkingLotId,
        @NotBlank
        String licensePlate,
        @NotNull
        VehicleType vehicleType
) {
}
