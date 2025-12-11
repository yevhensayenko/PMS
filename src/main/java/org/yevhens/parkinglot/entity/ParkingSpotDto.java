package org.yevhens.parkinglot.entity;

import org.yevhens.parkinglot.entity.embeddable.ParkingLevelId;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;

import java.io.Serializable;

/**
 * DTO for {@link ParkingSpot}
 */
public record ParkingSpotDto(Long parkingLotId,
                             Integer level,
                             Integer parkingSpotNo,
                             boolean available) implements Serializable {

    public static ParkingSpotDto fromEntity(ParkingSpot parkingSpot) {
        ParkingLevelId parkingLevelId = parkingSpot.getId().getParkingLevelId();
        return new ParkingSpotDto(parkingLevelId.getParkingLotId(),
                parkingLevelId.getParkingLevel(),
                parkingSpot.getId().getParkingSpotNo(),
                parkingSpot.isAvailable());
    }
}