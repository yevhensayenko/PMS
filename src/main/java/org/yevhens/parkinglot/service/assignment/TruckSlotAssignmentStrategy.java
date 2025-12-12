package org.yevhens.parkinglot.service.assignment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;
import org.yevhens.parkinglot.entity.vehicle.Truck;
import org.yevhens.parkinglot.repository.ParkingSpotRepository;
import org.yevhens.parkinglot.service.SlotAssignmentStrategy;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TruckSlotAssignmentStrategy implements SlotAssignmentStrategy<Truck> {

    private final ParkingSpotRepository parkingSpotRepository;

    @Override
    public Optional<? extends ParkingSpot> assignSpot(Truck vehicle, ParkingLot parkingLot) {
        return parkingSpotRepository.findAvailableLargeSpots(parkingLot.getId()).stream().findFirst();
    }
}
