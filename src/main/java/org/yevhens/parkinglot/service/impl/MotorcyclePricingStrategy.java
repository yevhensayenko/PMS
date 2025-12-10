package org.yevhens.parkinglot.service.impl;

import org.springframework.stereotype.Service;
import org.yevhens.parkinglot.entity.ParkingSession;
import org.yevhens.parkinglot.entity.vehicle.Motorcycle;
import org.yevhens.parkinglot.service.PricingStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Service
public class MotorcyclePricingStrategy implements PricingStrategy<Motorcycle> {

    public static final BigDecimal MOTORCYCLE_FEE = BigDecimal.valueOf(1);

    @Override
    public BigDecimal calculateFee(ParkingSession session) {
        Duration duration = Duration.between(session.getStartDateTime(), session.getFinishDateTime());
        return BigDecimal.valueOf(duration.getSeconds())
                .multiply(MOTORCYCLE_FEE)
                .divide(BigDecimal.valueOf(3600), 2, RoundingMode.UP);
    }
}