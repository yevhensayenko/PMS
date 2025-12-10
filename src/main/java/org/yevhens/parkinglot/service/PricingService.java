package org.yevhens.parkinglot.service;

import org.springframework.stereotype.Service;
import org.yevhens.parkinglot.entity.ParkingSession;
import org.yevhens.parkinglot.entity.vehicle.Vehicle;
import org.yevhens.parkinglot.util.ReflectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PricingService {

    private final Map<Class<? extends Vehicle>, PricingStrategy<? extends Vehicle>> strategies;

    public PricingService(List<PricingStrategy<?>> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(ReflectionUtils::getGenericClass, Function.identity()));
    }

    public BigDecimal calculateFee(ParkingSession parkingSession) {
        var strategy = strategies.get(parkingSession.getVehicle().getClass());
        if (strategy == null) {
            throw new UnsupportedOperationException("%s vehicle is not supported"
                    .formatted(parkingSession.getVehicle().getClass().getSimpleName()));
        }
        return strategy.calculateFee(parkingSession);
    }
}