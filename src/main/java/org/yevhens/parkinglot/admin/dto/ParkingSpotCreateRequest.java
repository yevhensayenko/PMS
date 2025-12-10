package org.yevhens.parkinglot.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.yevhens.parkinglot.entity.ParkingSpot;
import org.yevhens.parkinglot.model.ParkingSpotType;

import java.io.Serializable;

/**
 * DTO for {@link ParkingSpot}
 */
public record ParkingSpotCreateRequest(
        @NotNull
        @Min(1)
        Integer parkingSpotNo,

        @NotNull
        ParkingSpotType type
) implements Serializable {
}