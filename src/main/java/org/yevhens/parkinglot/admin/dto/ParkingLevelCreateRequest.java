package org.yevhens.parkinglot.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record ParkingLevelCreateRequest(
        @NotNull
        @Min(1)
        Integer level
) implements Serializable {
}