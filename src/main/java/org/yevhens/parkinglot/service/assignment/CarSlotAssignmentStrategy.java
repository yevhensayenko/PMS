package org.yevhens.parkinglot.service.assignment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.entity.spot.HandicappedSpot;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;
import org.yevhens.parkinglot.entity.vehicle.Car;
import org.yevhens.parkinglot.repository.ParkingSpotRepository;
import org.yevhens.parkinglot.service.SlotAssignmentStrategy;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarSlotAssignmentStrategy implements SlotAssignmentStrategy<Car> {

    private final ParkingSpotRepository parkingSpotRepository;

    @Override
    public Optional<? extends ParkingSpot> assignSpot(Car car, ParkingLot parkingLot) {
        if (car.isHandicapped()) {
            Optional<HandicappedSpot> availableHandicappedSpot = parkingSpotRepository.findAvailableHandicappedSpots(parkingLot.getId()).stream().findFirst();
            if (availableHandicappedSpot.isPresent()) {
                return availableHandicappedSpot;
            }
        }

        var availableCompactSpot = parkingSpotRepository.findAvailableCompactSpots(parkingLot.getId()).stream().findFirst();
        if (availableCompactSpot.isPresent()) {
            return availableCompactSpot;
        }

        return parkingSpotRepository.findAvailableLargeSpots(parkingLot.getId()).stream().findFirst();
    }
}
