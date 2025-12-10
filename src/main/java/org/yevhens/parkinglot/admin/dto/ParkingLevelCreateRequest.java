package org.yevhens.parkinglot.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.yevhens.parkinglot.model.ParkingSpotType;

import java.io.Serializable;

public record ParkingLevelCreateRequest(
        @NotNull ParkingSpotType type,
        @NotNull
        @Min(1)
        Integer level
) implements Serializable {
}