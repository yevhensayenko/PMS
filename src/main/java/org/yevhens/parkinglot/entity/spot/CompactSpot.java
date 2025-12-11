package org.yevhens.parkinglot.entity.spot;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.yevhens.parkinglot.entity.ParkingLevel;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;

@Entity
@DiscriminatorValue("COMPACT")
@AllArgsConstructor
public class CompactSpot extends ParkingSpot {

    @Builder
    public CompactSpot(ParkingSpotId id, ParkingLevel parkingLevel, boolean available) {
        super(id, parkingLevel, available);
    }
}