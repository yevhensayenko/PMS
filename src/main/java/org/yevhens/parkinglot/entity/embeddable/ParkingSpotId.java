package org.yevhens.parkinglot.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ParkingSpotId {

    private Long parkingLotId;
    private Integer parkingSpotNo;

    public ParkingSpotId() {}

    public ParkingSpotId(Long parkingLotId, Integer parkingSpotNo) {
        this.parkingLotId = parkingLotId;
        this.parkingSpotNo = parkingSpotNo;
    }
}