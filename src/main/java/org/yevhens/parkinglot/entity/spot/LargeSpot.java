package org.yevhens.parkinglot.entity.spot;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.yevhens.parkinglot.entity.ParkingLevel;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;

@Entity
@DiscriminatorValue("LARGE")
@AllArgsConstructor
public class LargeSpot extends ParkingSpot {

    @Builder
    public LargeSpot(ParkingSpotId id, ParkingLevel parkingLevel, boolean available) {
        super(id, parkingLevel, available);
    }
}