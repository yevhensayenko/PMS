package org.yevhens.parkinglot.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.yevhens.parkinglot.entity.ParkingLot;

import java.io.Serializable;

/**
 * DTO for {@link ParkingLot}
 */
public record ParkingLotDto(
        @NotBlank String name,
        @Positive int levelCount
) implements Serializable {
}