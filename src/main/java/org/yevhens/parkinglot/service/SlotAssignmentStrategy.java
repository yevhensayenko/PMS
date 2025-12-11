package org.yevhens.parkinglot.service;

import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;
import org.yevhens.parkinglot.entity.vehicle.Vehicle;

import java.util.Optional;

public interface SlotAssignmentStrategy<T extends Vehicle> {

    Optional<? extends ParkingSpot> assignSpot(T vehicle, ParkingLot parkingLot);

}