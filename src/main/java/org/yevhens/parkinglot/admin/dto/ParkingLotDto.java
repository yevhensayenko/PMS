package org.yevhens.parkinglot.admin.dto;

import org.yevhens.parkinglot.entity.ParkingLot;

import java.io.Serializable;

/**
 * DTO for {@link ParkingLot}
 */
public record ParkingLotDto(Long id, String name) implements Serializable {

    public static ParkingLotDto fromEntity(ParkingLot parkingLot) {
        return new ParkingLotDto(parkingLot.getId(), parkingLot.getName());
    }
}