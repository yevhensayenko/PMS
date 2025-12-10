package org.yevhens.parkinglot.model;

import lombok.Getter;

@Getter
public enum VehicleType {
    CAR(ParkingSpotType.COMPACT),
    MOTORCYCLE(ParkingSpotType.MOTORCYCLE),
    TRUCK(ParkingSpotType.LARGE);

    private final ParkingSpotType parkingSpotType;

    VehicleType(ParkingSpotType parkingSpotType) {
        this.parkingSpotType = parkingSpotType;
    }
}
