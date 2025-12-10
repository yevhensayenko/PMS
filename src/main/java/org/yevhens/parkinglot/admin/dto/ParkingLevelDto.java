package org.yevhens.parkinglot.admin.dto;

import org.yevhens.parkinglot.entity.ParkingLevel;

import java.io.Serializable;

/**
 * DTO for {@link ParkingLevel}
 */
public record ParkingLevelDto(Long parkingLotId, Integer parkingLevel) implements Serializable {

    public static ParkingLevelDto fromEntity(ParkingLevel parkingLot) {
        return new ParkingLevelDto(parkingLot.getId().getParkingLotId(), parkingLot.getId().getParkingLevel());
    }
}