package org.yevhens.parkinglot.entity.spot;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.yevhens.parkinglot.entity.ParkingLevel;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;

@Entity
@DiscriminatorValue("MOTORCYCLE")
@AllArgsConstructor
public class MotorcycleSpot extends ParkingSpot {

    @Builder
    public MotorcycleSpot(ParkingSpotId id, ParkingLevel parkingLevel, boolean available) {
        super(id, parkingLevel, available, null);
    }
}