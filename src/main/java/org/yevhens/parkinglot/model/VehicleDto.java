package org.yevhens.parkinglot.model;

import jakarta.validation.constraints.NotNull;
import org.yevhens.parkinglot.entity.vehicle.Vehicle;

import java.io.Serializable;

/**
 * DTO for {@link Vehicle}
 */
public record VehicleDto(@NotNull String licensePlate) implements Serializable {

    public static VehicleDto fromDto(Vehicle vehicle) {
        return new VehicleDto(vehicle.getLicensePlate());
    }
}