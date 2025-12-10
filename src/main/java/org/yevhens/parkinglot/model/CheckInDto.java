package org.yevhens.parkinglot.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckInDto(
        @NotBlank
        String licensePlate,
        @NotNull
        VehicleType vehicleType
) {
}
