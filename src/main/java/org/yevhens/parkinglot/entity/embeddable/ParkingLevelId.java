package org.yevhens.parkinglot.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ParkingLevelId {

    private Long parkingLotId;
    private Integer parkingLevel;

    public ParkingLevelId() {}

    public ParkingLevelId(Long parkingLotId, Integer parkingLevel) {
        this.parkingLotId = parkingLotId;
        this.parkingLevel = parkingLevel;
    }
}