package org.yevhens.parkinglot.util;

import org.yevhens.parkinglot.entity.ParkingLevel;
import org.yevhens.parkinglot.entity.embeddable.ParkingLevelId;
import org.yevhens.parkinglot.entity.embeddable.ParkingSpotId;
import org.yevhens.parkinglot.entity.spot.CompactSpot;
import org.yevhens.parkinglot.entity.spot.HandicappedSpot;
import org.yevhens.parkinglot.entity.spot.LargeSpot;
import org.yevhens.parkinglot.entity.spot.MotorcycleSpot;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;
import org.yevhens.parkinglot.model.ParkingSpotType;

public final class ParkingSpotFactory {

    private ParkingSpotFactory() {
    }

    public static ParkingSpot create(ParkingSpotType type, ParkingLevel parkingLevel, Integer parkingSpotNo, boolean available) {
        final ParkingLevelId parkingLevelId = parkingLevel.getId();
        final var parkingSpotId = new ParkingSpotId(parkingLevelId.getParkingLotId(), parkingLevelId.getParkingLevel(), parkingSpotNo);

        return switch (type) {
            case COMPACT -> CompactSpot.builder()
                    .id(parkingSpotId)
                    .parkingLevel(parkingLevel)
                    .available(available)
                    .build();
            case LARGE -> LargeSpot.builder()
                    .id(parkingSpotId)
                    .parkingLevel(parkingLevel)
                    .available(available)
                    .build();
            case MOTORCYCLE -> MotorcycleSpot.builder()
                    .id(parkingSpotId)
                    .parkingLevel(parkingLevel)
                    .available(available)
                    .build();
            case HANDICAPPED -> HandicappedSpot.builder()
                    .id(parkingSpotId)
                    .parkingLevel(parkingLevel)
                    .available(available)
                    .build();
        };
    }
}