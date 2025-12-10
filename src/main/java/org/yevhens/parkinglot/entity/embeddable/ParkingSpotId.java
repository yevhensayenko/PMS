package org.yevhens.parkinglot.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ParkingSpotId {

    private ParkingLevelId parkingLevelId;
    private Integer parkingSpotNo;

    public ParkingSpotId() {}

    public ParkingSpotId(Long parkingLotId, Integer parkingLevel, Integer parkingSpotNo) {
        this.parkingLevelId = new ParkingLevelId(parkingLotId, parkingLevel);
        this.parkingSpotNo = parkingSpotNo;
    }
}