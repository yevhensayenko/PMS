package org.yevhens.parkinglot.service;

import org.springframework.stereotype.Service;
import org.yevhens.parkinglot.entity.ParkingLot;
import org.yevhens.parkinglot.entity.spot.ParkingSpot;
import org.yevhens.parkinglot.entity.vehicle.Vehicle;
import org.yevhens.parkinglot.util.ReflectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SpotAssignmentService {

    private final Map<Class<? extends Vehicle>, SlotAssignmentStrategy<? extends Vehicle>> strategies;

    public SpotAssignmentService(List<SlotAssignmentStrategy<? extends Vehicle>> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(ReflectionUtils::getGenericClass, Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public Optional<? extends ParkingSpot> assignSpot(Vehicle vehicle, ParkingLot parkingLot) {
        return Optional.of(strategies.get(vehicle))
                .map(SlotAssignmentStrategy.class::cast)
                .flatMap(strategy -> strategy.assignSpot(vehicle, parkingLot));
    }
}
