package org.yevhens.parkinglot.admin.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.yevhens.parkinglot.entity.ParkingSpot;
import org.yevhens.parkinglot.model.VehicleType;

import java.io.Serializable;

/**
 * DTO for {@link ParkingSpot}
 */
public record ParkingSpotDto(
        @NotNull
        @Positive
        Long parkingLotId,

        @NotNull
        @Positive
        Integer parkingSpotNo,

        @NotNull VehicleType vehicleType,
        @NotNull
        @Positive
        Integer level
) implements Serializable {
}