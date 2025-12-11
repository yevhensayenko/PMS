package org.yevhens.parkinglot.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;
import org.yevhens.parkinglot.entity.vehicle.Vehicle;
import org.yevhens.parkinglot.repository.ParkingSpotRepository;
import org.yevhens.parkinglot.util.ReflectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SpotAssignmentService {

    private final Map<Class<? extends Vehicle>, SlotAssignmentStrategy<? extends Vehicle>> strategies;
    private final ParkingSpotRepository parkingSpotRepository;

    public SpotAssignmentService(List<SlotAssignmentStrategy<? extends Vehicle>> strategies,
                                 ParkingSpotRepository parkingSpotRepository) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(ReflectionUtils::getGenericClass, Function.identity()));
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @SuppressWarnings("unchecked")
    public Optional<? extends ParkingSpot> assignSpot(Vehicle vehicle, ParkingLot parkingLot) {
        final var vehicleClass = Hibernate.getClass(vehicle);

        Optional<ParkingSpot> availableSpot = Optional.of(strategies.get(vehicleClass))
                .map(SlotAssignmentStrategy.class::cast)
                .flatMap(strategy -> strategy.assignSpot(vehicle, parkingLot));

        return availableSpot.map(this::markOccupied);
    }

    private ParkingSpot markOccupied(ParkingSpot parkingSpot) {
        parkingSpot.setAvailable(false);
        return parkingSpotRepository.save(parkingSpot);
    }
}
