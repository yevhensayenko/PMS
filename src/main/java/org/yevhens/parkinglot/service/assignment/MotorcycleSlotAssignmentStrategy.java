package org.yevhens.parkinglot.service.assignment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;
import org.yevhens.parkinglot.entity.vehicle.Motorcycle;
import org.yevhens.parkinglot.repository.ParkingSpotRepository;
import org.yevhens.parkinglot.service.SlotAssignmentStrategy;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MotorcycleSlotAssignmentStrategy implements SlotAssignmentStrategy<Motorcycle> {

    private final ParkingSpotRepository parkingSpotRepository;

    @Override
    public Optional<? extends ParkingSpot> assignSpot(Motorcycle motorcycle, ParkingLot parkingLot) {
        var availableMotorcycleSpot = parkingSpotRepository.findAvailableMotorcycleSpots(parkingLot.getId()).stream().findFirst();
        if (availableMotorcycleSpot.isPresent()) {
            return availableMotorcycleSpot;
        }

        var availableCompactSpot = parkingSpotRepository.findAvailableCompactSpots(parkingLot.getId()).stream().findFirst();
        if (availableCompactSpot.isPresent()) {
            return availableCompactSpot;
        }

        return parkingSpotRepository.findAvailableLargeSpots(parkingLot.getId()).stream().findFirst();
    }
}
