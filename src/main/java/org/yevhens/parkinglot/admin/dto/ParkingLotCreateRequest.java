package org.yevhens.parkinglot.admin.dto;

import jakarta.validation.constraints.NotBlank;
import org.yevhens.parkinglot.entity.ParkingLot;

import java.io.Serializable;

/**
 * DTO for {@link ParkingLot}
 */
public record ParkingLotCreateRequest(
        @NotBlank String name
) implements Serializable {
}
